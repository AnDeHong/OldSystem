package com.example.services;

import com.example.common.vo.DictTypeVo;
import com.example.entity.AdminImage;
import com.example.entity.Dish;
import com.example.entity.OperationLog;
import com.example.entity.Package;
import com.example.mapper.AdminImageMapper;
import com.example.mapper.DishMapper;
import com.example.mapper.OperationLogMapper;
import com.example.mapper.PackageMapper;
import com.example.response.TaskException;
import com.example.services.Iservices.IAdminImageServices;
import com.example.services.Iservices.IDishServices;
import com.example.services.Iservices.ILogServices;
import com.example.services.Iservices.IPackageServices;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import com.example.vo.DishVo;
import com.example.vo.PackageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class PackageServices implements IPackageServices {
    @Resource
    private PackageMapper packageMapper;

    @Resource
    private IDishServices iDishServices;
    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;
    @Resource
    private AdminImageMapper adminImageMapper;
    @Resource
    private IAdminImageServices iAdminImageServices;

    public Boolean insert(Package packages, MultipartFile fileUpload) {
        if (packages != null && fileUpload != null) {
            String ids = String.format("%s,%s,%s,%s",packages.getZhuShi(),packages.getZhuCai(),packages.getFuShi(),packages.getFuCai());
            packages.setPrice(this.iDishServices.getAllPrice(ids));
            if (packages.getDiscount() > 0) {
                Float discountPrice = packages.getPrice()*packages.getDiscount();
                discountPrice = (float) (Math.round(discountPrice * 100.0) / 100.0);
                packages.setDiscountPrice(discountPrice);
            } else {
                packages.setDiscountPrice(packages.getPrice());
            }
            packages.setDishIds(ids);
            packages.setTotal(0);
            packages.init(UserUtils.getUser());
            packages.setImageUrl(this.addImage(packages,fileUpload));
            if (this.packageMapper.insert(packages) > 0) {
                packages.setPackageNo(Const.PACKAGE_NO+packages.getId());
                this.packageMapper.updateById(packages);
                String operation = String.format(Const.ADD+Const.PACKAGE);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
            return false;
        }
        return false;
    }

    public Boolean update(Package dish, MultipartFile fileUpload) {
        if (fileUpload != null) {
            String uuid = this.iDishServices.getUuid(dish.getImageUrl());
            AdminImage adminImage = this.adminImageMapper.getImageByUuid(uuid);
            if (adminImage != null) {
                Map<String, String> newImg = this.iAdminImageServices.addImageDish(fileUpload);
                adminImage.setUuid(newImg.get("uuid"));
                adminImage.setValue(newImg.get("fileName"));
                this.adminImageMapper.updateById(adminImage);
                dish.setImageUrl(String.format("http://127.0.0.1:8808/admin-services/admin-image/get/%s",newImg.get("uuid")));
                dish.init(UserUtils.getUser().getUsername(),new Date());
                if (this.packageMapper.updateById(dish) > 0) {
                    String operation = String.format(Const.UPDATE+Const.PACKAGE);
                    OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
                    this.operationLogMapper.insert(operationLog);
                    return true;
                }
            }
            return false;
        }
        Package pa = this.packageMapper.selectById(dish.getId());
        pa.init(UserUtils.getUser().getUsername(),new Date());
        pa.setDiscount(dish.getDiscount());
        pa.setTags(dish.getTags());
        if (dish.getDiscount() > 0) {
            Float discountPrice = pa.getPrice()*dish.getDiscount();
            discountPrice = (float) (Math.round(discountPrice * 100.0) / 100.0);
            pa.setDiscountPrice(discountPrice);
        } else {
            pa.setDiscountPrice(pa.getPrice()*dish.getDiscount());
        }
        pa.setZhuCai(dish.getZhuCai());
        pa.setZhuShi(dish.getZhuShi());
        pa.setFuCai(dish.getFuCai());
        pa.setFuShi(dish.getFuShi());
        pa.setIntroduce(dish.getIntroduce());
        pa.setIsShow(dish.getIsShow());
        pa.setPackageName(dish.getPackageName());
        if (this.packageMapper.updateById(pa) > 0) {
            String operation = String.format(Const.UPDATE+Const.PACKAGE);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }

    public Boolean delete(int id) {
        Package packages = this.packageMapper.selectById(id);
        if (packages != null) {
            String uuid = this.iDishServices.getUuid(packages.getImageUrl());
            AdminImage image = this.adminImageMapper.getImageByUuid(uuid);
            image.init(UserUtils.getUser().getUsername(),new Date());
            image.setDeleteMark(Const.YES);
            this.adminImageMapper.updateById(image);
            packages.init(UserUtils.getUser().getUsername(),new Date());
            packages.setDeleteMark(Const.YES);
            String operation = String.format(Const.DELETE+Const.PACKAGE);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return this.packageMapper.updateById(packages) > 0;
        }
        return false;
    }

    public List<PackageVo> getAll() {
        List<Package> packages = this.packageMapper.getAll();
        List<PackageVo> packageVoList = new ArrayList<>();
        if (packages != null && !packages.isEmpty()) {
            for (Package item : packages) {
                PackageVo packageVo = new PackageVo();
                packageVo.setId(item.getId());
                packageVo.setPackageName(item.getPackageName());
                packageVo.setDiscount(item.getDiscount());
                packageVo.setPrice(item.getPrice());
                packageVo.setTags(item.getTags());
                packageVo.setIntroduce(item.getIntroduce());
                packageVo.setUpdatedTime(item.getUpdatedTime());
                packageVo.setDiscountPrice(item.getDiscountPrice());
                packageVo.setIsShow(item.getIsShow());
                packageVo.setImageUrl(item.getImageUrl());
                packageVo.setTotal(item.getTotal());
                packageVo.setZhuShi(item.getZhuShi());
                packageVo.setZhuCai(item.getZhuCai());
                packageVo.setFuShi(item.getFuShi());
                packageVo.setFuCai(item.getFuCai());
                String[] ids = item.getDishIds().split(",");
                List<Dish> dishList = this.iDishServices.getByIds(ids);
                packageVo.setDishes(dishList);
                packageVoList.add(packageVo);
            }
        }
        return packageVoList;
    }
    public String addImage(Package vo, MultipartFile fileUpload) {
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        //重新生成文件名
        fileName = uuid + suffixName;
        // 添加图片大小判断
        long fileSizeInBytes = fileUpload.getSize();
        long fileSizeInKB = fileSizeInBytes / (1024 * 1024); // 文件大小以KB为单位
        if(fileSizeInKB > 10) {
            throw new TaskException("上传文件过大，请上传小于10MB的图片");
        }
        //指定本地文件夹存储图片，写到需要保存的目录下
        String filePath = "E:/DeskTop/gradulationDesign/admin/springCloud-demo1/image/dish/" + fileName;
        String url = String.format("http://127.0.0.1:8808/admin-services/admin-image/get/%s",uuid);
        System.out.println(filePath);
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(System.currentTimeMillis());
            System.out.println(time);  //2021-10-29:00:11:16

            AdminImage image = new AdminImage(DictTypeVo.DISHIMG.getCode(), uuid,Const.PACKAGE,filePath,vo.getIntroduce(),url);
            //返回提示信息
            if (this.adminImageMapper.insert(image) > 0) {
                try {
                    Thread.sleep(1000); // 1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return this.iDishServices.getImage(uuid);
            }
            return null;
        } catch (Exception e) {
            throw new TaskException("上传文件失败:" + e);
        }
    }

    @Override
    public Boolean updateStatus(Package dish) {
        if (dish.getIsShow().equals("上架")) {
            dish.init(UserUtils.getUser().getUsername(), new Date());
            dish.setIsShow(Const.NOSHOW);
            if (this.packageMapper.updateById(dish) > 0) {
                String operation = String.format(Const.UPDATE + Const.PACKAGE + Const.STATUS);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(), operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        if (dish.getIsShow().equals("下架")) {
            dish.init(UserUtils.getUser().getUsername(), new Date());
            dish.setIsShow(Const.SHOW);
            if (this.packageMapper.updateById(dish) > 0) {
                String operation = String.format(Const.UPDATE + Const.PACKAGE + Const.STATUS);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(), operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        return false;
    }

    public List<PackageVo> getByStatus(String name, String type,String status) {
        List<Package> packages = this.packageMapper.getByStatus(name,type,status);
        List<PackageVo> packageVoList = new ArrayList<>();
        if (packages != null && !packages.isEmpty()) {
            for (Package item : packages) {
                PackageVo packageVo = new PackageVo();
                packageVo.setId(item.getId());
                packageVo.setPackageName(item.getPackageName());
                packageVo.setDiscount(item.getDiscount());
                packageVo.setPrice(item.getPrice());
                packageVo.setTags(item.getTags());
                packageVo.setIntroduce(item.getIntroduce());
                packageVo.setUpdatedTime(item.getUpdatedTime());
                packageVo.setDiscountPrice(item.getDiscountPrice());
                packageVo.setIsShow(item.getIsShow());
                packageVo.setImageUrl(item.getImageUrl());
                packageVo.setTotal(item.getTotal());
                packageVo.setZhuShi(item.getZhuShi());
                packageVo.setZhuCai(item.getZhuCai());
                packageVo.setFuShi(item.getFuShi());
                packageVo.setFuCai(item.getFuCai());
                String[] ids = item.getDishIds().split(",");
                List<Dish> dishList = this.iDishServices.getByIds(ids);
                packageVo.setDishes(dishList);
                packageVoList.add(packageVo);
            }
        }
        return packageVoList;
    }
}

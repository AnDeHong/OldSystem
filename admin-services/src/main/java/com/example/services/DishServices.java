package com.example.services;

import com.example.common.vo.DictTypeVo;
import com.example.entity.AdminImage;
import com.example.entity.Dish;
import com.example.entity.OperationLog;
import com.example.mapper.AdminImageMapper;
import com.example.mapper.DishMapper;
import com.example.mapper.OperationLogMapper;
import com.example.response.TaskException;
import com.example.services.Iservices.IAdminImageServices;
import com.example.services.Iservices.IDishServices;
import com.example.services.Iservices.ILogServices;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import com.example.vo.DishVo;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DishServices implements IDishServices {
    @Resource
    private DishMapper dishMapper;
    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;
    @Resource
    private AdminImageMapper adminImageMapper;
    @Resource
    private IAdminImageServices iAdminImageServices;


    public Boolean insert(DishVo vo, MultipartFile fileUpload) {
        Dish dish = new Dish();
        if (vo != null && fileUpload != null) {
            dish.setDishName(vo.getDishName());
            dish.setDiscounts(vo.getDiscounts());
            dish.setIntroduce(vo.getIntroduce());
            dish.setPrice(vo.getPrice());
            dish.setTag(vo.getTag());
            dish.setType(vo.getType());
            if (vo.getDiscounts() == 0.0) {
                dish.setDiscountsPrice(vo.getPrice());
            } else {
                dish.setDiscountsPrice(vo.getPrice()*vo.getDiscounts());
            }
            dish.setImageUrl(this.addImage(vo,fileUpload));
            dish.setRecommendationLevel(vo.getRecommendationLevel());
            dish.setIsShow(Const.SHOW);
            dish.setTotal(0);
            dish.init(UserUtils.getUser().getUsername(),new Date());
            String operation = String.format(Const.ADD+Const.DISH);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            dish.init(UserUtils.getUser());
            if (this.dishMapper.insert(dish) > 0) {
                dish.setDishNo(Const.DISH_NO+dish.getId());
                this.dishMapper.updateById(dish);
            }
            return true;
        }
        return false;
    }

    public List<Dish> getAll() {
        return this.dishMapper.getAll();
    }

    public Boolean update(Dish dish, MultipartFile fileUpload) {
        if (fileUpload != null) {
            String uuid = this.getUuid(dish.getImageUrl());
            AdminImage adminImage = this.adminImageMapper.getImageByUuid(uuid);
            if (adminImage != null) {
                Map<String, String> newImg = this.iAdminImageServices.addImageDish(fileUpload);
                adminImage.setUuid(newImg.get("uuid"));
                adminImage.setValue(newImg.get("fileName"));
                this.adminImageMapper.updateById(adminImage);
                dish.setImageUrl(String.format("http://127.0.0.1:8808/admin-services/admin-image/get/%s",newImg.get("uuid")));
                dish.init(UserUtils.getUser().getUsername(),new Date());
                if (dish.getDiscounts() == 0.0) {
                    dish.setDiscountsPrice(dish.getPrice());
                } else {
                    dish.setDiscountsPrice(dish.getPrice()*dish.getDiscounts());
                }
                if (this.dishMapper.updateById(dish) > 0) {
                    String operation = String.format(Const.UPDATE+Const.DISH);
                    OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
                    this.operationLogMapper.insert(operationLog);
                    return true;
                }
            }
            return false;
        }
        dish.init(UserUtils.getUser().getUsername(),new Date());
        if (dish.getDiscounts() == 0.0) {
            dish.setDiscountsPrice(dish.getPrice());
        } else {
            dish.setDiscountsPrice(dish.getPrice()*dish.getDiscounts());
        }
        if (this.dishMapper.updateById(dish) > 0) {
            String operation = String.format(Const.UPDATE+Const.DISH);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return true;
        }
        return false;
    }

    public Boolean delete(int id) {
        Dish dish = this.dishMapper.selectById(id);
        if (dish != null) {
            String uuid = this.getUuid(dish.getImageUrl());
            AdminImage image = this.adminImageMapper.getImageByUuid(uuid);
            image.init(UserUtils.getUser().getUsername(),new Date());
            image.setDeleteMark(Const.YES);
            this.adminImageMapper.updateById(image);
            dish.init(UserUtils.getUser().getUsername(),new Date());
            dish.setDeleteMark(Const.YES);
            String operation = String.format(Const.DELETE+Const.DISH);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            return this.dishMapper.updateById(dish) > 0;
        }
        return false;
    }

    public List<Dish> getByType(String type) {
        return this.dishMapper.getByType(type);
    }

//    批量通过id获取dish
    @Override
    public List<Dish> getByIds(String[] ids) {
        if (ids.length == 0) {
            return null;
        }
        List<Dish> dishList = new ArrayList<>();
        for (String i : ids) {
            Dish dish = this.dishMapper.getPackDish(i);
            dishList.add(dish);
        }
        return dishList;
    }
    @Override
    public List<Dish> getByNo(String[] ids) {
        if (ids.length == 0) {
            return null;
        }
        List<Dish> dishList = new ArrayList<>();
        for (String i : ids) {
            Dish dish = this.dishMapper.getPackDishByNo(i);
            dishList.add(dish);
        }
        return dishList;
    }

//    将图片保存到数据库
    public String addImage(DishVo vo,MultipartFile fileUpload) {
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

            AdminImage image = new AdminImage(DictTypeVo.DISHIMG.getCode(), uuid,vo.getDishName(),filePath,vo.getIntroduce(),url);
            //返回提示信息
            if (adminImageMapper.insert(image) > 0) {
                String operation = String.format(Const.ADD+Const.DISH);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
                this.operationLogMapper.insert(operationLog);
                try {
                    Thread.sleep(1000); // 1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return this.getImage(uuid);
            }
            return null;
        } catch (Exception e) {
            throw new TaskException("上传文件失败:" + e);
        }
    }
    @Override
    public String getImage(String uuid) throws IOException {
        AdminImage adminImage = this.adminImageMapper.getImageByUuid(uuid);
        if (adminImage == null) {
            return null;
        }
        return String.format("http://127.0.0.1:8808/admin-services/admin-image/get/%s",adminImage.getUuid());
    }

    @Override
    public String getUuid(String url) {
        if(!StringUtils.isNullOrEmpty(url)) {
            String[] list = url.split("/");
            return list[6];
        }
        return null;
    }


    @Override
    public Boolean updateStatus(Dish dish) {
        if (dish.getIsShow().equals("上架")) {
            dish.init(UserUtils.getUser().getUsername(), new Date());
            dish.setIsShow(Const.NOSHOW);
            if (this.dishMapper.updateById(dish) > 0) {
                String operation = String.format(Const.UPDATE + Const.DISH + Const.STATUS);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(), operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        if (dish.getIsShow().equals("下架")) {
            dish.init(UserUtils.getUser().getUsername(), new Date());
            dish.setIsShow(Const.SHOW);
            if (this.dishMapper.updateById(dish) > 0) {
                String operation = String.format(Const.UPDATE + Const.DISH + Const.STATUS);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(), operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        return false;
    }

    @Override
    public Float getAllPrice(String ids) {
        String[] names = ids.split(",");
        Float price = 0.0F;
        for (String name : names) {
            Dish dish = this.dishMapper.getPackDish(name);
            if (dish != null) {
                price+= dish.getDiscountsPrice();
            }
        }
        return price;
    }

    public List<Dish> getDishes(String name, String type,String status) {
        return this.dishMapper.getDishes(name,type,status);
    }

    public List<Map<String, String>> getDishName(String type) {
        List<DishVo> list = this.dishMapper.getDishName(type);
        List<Map<String, String>> target = new ArrayList<>();
        if (list.size() > 0) {
            for (DishVo dishVo: list) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(dishVo.getId()));
                map.put("name",dishVo.getDishName());
                target.add(map);
            }
        }
        return target;
    }
}

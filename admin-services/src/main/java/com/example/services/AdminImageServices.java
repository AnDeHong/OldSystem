package com.example.services;

import com.example.entity.Admin;
import com.example.entity.AdminImage;
import com.example.entity.OperationLog;
import com.example.mapper.AdminImageMapper;
import com.example.mapper.OperationLogMapper;
import com.example.response.TaskException;
import com.example.services.Iservices.IAdminImageServices;
import com.example.services.Iservices.ILogServices;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class AdminImageServices implements IAdminImageServices {
    @Resource
    public AdminImageMapper adminImageMapper;
    @Resource
    private OperationLogMapper operationLogMapper;
    @Resource
    private ILogServices iLogServices;

    public Boolean addImage(String code,String name, String text, MultipartFile fileUpload) throws FileNotFoundException {
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
        String filePath = "E:/DeskTop/gradulationDesign/admin/springCloud-demo1/image/" + fileName;
        String url = String.format("http://127.0.0.1:8808/admin-services/admin-image/get/%s",uuid);
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(System.currentTimeMillis());
            System.out.println(time);  //2021-10-29:00:11:16

            AdminImage image = new AdminImage(code,uuid,name,filePath,text,url);
            adminImageMapper.insert(image);
            String operation = String.format(Const.ADD+Const.IMG);
            OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
            this.operationLogMapper.insert(operationLog);
            //返回提示信息
            return true;
        } catch (Exception e) {
            throw new TaskException("上传文件失败:" + e);
        }
    }

    public List<AdminImage> getAll() {
        return this.adminImageMapper.getAll();
    }

    public Boolean updateStatus(AdminImage image) {
        if (image.getIsShow().equals(Const.NOSHOW)) {
            image.init(UserUtils.getUser().getUsername(),new Date());
            image.setIsShow(Const.SHOW);
            if (this.adminImageMapper.updateById(image) > 0) {
                String operation = String.format(Const.UPDATE+Const.IMG+Const.STATUS);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        if (image.getIsShow().equals(Const.SHOW)) {
            image.setIsShow(Const.NOSHOW);
            if (this.adminImageMapper.updateById(image) > 0) {
                String operation = String.format(Const.UPDATE+Const.IMG+Const.STATUS);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteImage(String uuid) {
        AdminImage image = this.adminImageMapper.getImageByUuid(uuid);
        if(image != null) {
            image.init(UserUtils.getUser().getUsername(),new Date());
            image.setDeleteMark(Const.YES);
            if(this.adminImageMapper.updateById(image) > 0) {
                String operation = String.format(Const.DELETE + Const.IMG);
                OperationLog operationLog = this.iLogServices.write(UserUtils.getUser().getUsername(),operation);
                this.operationLogMapper.insert(operationLog);
                return true;
            }
        }
        return false;
    }

    public byte[] getImage(String uuid) throws IOException {
        AdminImage image = this.adminImageMapper.getImageByUuid(uuid);
        if (image == null) {
            return null;
        }
        String path = image.getValue();
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        return IOUtils.toByteArray(inputStream);
    }

    //    将图片保存到文件夹
    @Override
    public Map<String, String> addImageDish(MultipartFile fileUpload) {
        Map<String, String> map = new HashMap<>();
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
        System.out.println(filePath);
        try {
            fileUpload.transferTo(new File(filePath));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(System.currentTimeMillis());
            System.out.println(time);  //2021-10-29:00:11:16
            map.put("uuid",uuid);
            map.put("fileName",fileName);
            return map;
        } catch (Exception e) {
            throw new TaskException("上传文件失败:" + e);
        }
    }

    public List<AdminImage> getByParams(String code,String name,String status) {
        return this.adminImageMapper.getByParams(code,name,status);
    }

}
package com.example.services;

import com.example.common.vo.DictTypeVo;
import com.example.entity.AdminImage;
import com.example.entity.Family;
import com.example.entity.UserInfo;
import com.example.mapper.FamilyMapper;
import com.example.mapper.UserImageMapper;
import com.example.mapper.UserMapper;
import com.example.response.TaskException;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import com.example.vo.DishVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public class FamilyServices {
    @Resource
    private FamilyMapper familyMapper;
    @Resource
    private UserImageMapper userImageMapper;
    @Resource
    private UserMapper userMapper;

    public Boolean inert(Family family, MultipartFile file) {
        if (family != null) {
            String url = this.addImage(family,file);
            family.setAvatarUrl(url);
            if(this.familyMapper.insert(family) > 0) {
                return true;
            }
        }
        return false;
    }

    public Boolean update(Family family) {
        return this.familyMapper.updateById(family) > 0;
    }

    public String addImage(Family family, MultipartFile fileUpload) {
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
        String filePath = "E:/DeskTop/gradulationDesign/admin/springCloud-demo1/image/user/" + fileName;
        String url = String.format("http://127.0.0.1:8808/user-services/user-image/%s",uuid);
        System.out.println(filePath);
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(System.currentTimeMillis());
            System.out.println(time);  //2021-10-29:00:11:16

            AdminImage image = new AdminImage("user",uuid,family.getName(),filePath,family.getName(),url);
            UserInfo userInfo = this.userMapper.getUserByOpenId(UserUtils.getUser().getToken());
            image.userInit(userInfo.getUsername());
            //返回提示信息
            if (userImageMapper.insert(image) > 0) {
                return url;
            }
            return null;
        } catch (Exception e) {
            throw new TaskException("上传文件失败:" + e);
        }
    }
}

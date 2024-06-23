package com.example.services;

import com.example.entity.AdminImage;
import com.example.mapper.UserImageMapper;
import com.example.response.TaskException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class UserImageServices {
    @Resource
    private UserImageMapper userImageMapper;

    public List<Map<String, String>> getUserImage(String code) throws IOException {
        List<AdminImage> lists = this.userImageMapper.getUserImage(code);
        if (lists.isEmpty()) {
            return null;
        }
        List<Map<String, String>> images = new ArrayList<>();
        for (AdminImage list : lists) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(list.getId()));
            map.put("text", list.getText());
            map.put("url", String.format("http://127.0.0.1:8808/user-services/user-image/%s",list.getUuid()));
            images.add(map);
        }
        return images;
    }

    public byte[] getImageByName(String uuid) throws IOException {
        AdminImage image = this.userImageMapper.getImageByUuid(uuid);
        if (image == null) {
            return null;
        }
        String path = image.getValue();
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        return IOUtils.toByteArray(inputStream);
    }


    public String convertToBase64(String path) throws IOException {
        //使用spring boot自带的ResourceUtils从资源路径中获取文件
        File file = ResourceUtils.getFile(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        //使用IO流将其转换为字节数组
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        //将字节转换为base64
        String encodeBase64 = Base64.encodeBase64String(bytes);
        //关闭IO流
        fileInputStream.close();
        return encodeBase64;
    }

    public byte[] getByNameAndCode(String name, String code) throws IOException {
        AdminImage image = this.userImageMapper.getByNameAndCode(name, code);
        return this.getImageByName(image.getUuid());
    }
}

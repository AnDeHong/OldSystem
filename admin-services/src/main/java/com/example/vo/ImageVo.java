package com.example.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageVo {
    private String uuid;
    private String text;
    private MultipartFile file;
}

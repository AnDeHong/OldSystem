package com.example.services.Iservices;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IAdminImageServices {
    Map<String, String> addImageDish(MultipartFile fileUpload);
}

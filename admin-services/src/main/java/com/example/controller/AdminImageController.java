package com.example.controller;

import com.example.common.vo.DictTypeVo;
import com.example.entity.AdminImage;
import com.example.entity.Dish;
import com.example.response.Response;
import com.example.services.AdminImageServices;
import com.example.services.DictServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin-image")
@Api(tags = "上传图片", description = "上传图片")
public class AdminImageController extends BaseController {

    @Autowired
    public AdminImageServices adminImageServices;

    @Resource
    public DictServices dictServices;

    @PostMapping("/upload")
    @ApiOperation("上传图片")
    public Response<Boolean> upload(@RequestParam("code") String code,@RequestParam("name") String name,@RequestParam("text") String text, @RequestParam("fileUpload") MultipartFile fileUpload) throws IOException {
//        String newText = ESAPI.encoder().encodeForHTML(text);
        Boolean res = adminImageServices.addImage(code,name, text, fileUpload);
        return this.ok(res);
    }

    @GetMapping("/get-all")
    @ApiOperation("获取全部图片")
    public Response<List<AdminImage>> getAll(){
        return this.ok(this.adminImageServices.getAll());
    }

    @PostMapping("/update")
    @ApiOperation("修改图片是否使用的状态")
    public Response<Boolean> updateStatus(@RequestBody AdminImage adminImage){
        return this.ok(this.adminImageServices.updateStatus(adminImage));
    }
    @GetMapping("/get-values")
    @ApiOperation("获取图片的value值")
    public Response<String[]> getValues(){
        return this.ok(this.dictServices.getValues(String.valueOf(DictTypeVo.IMAGE)));
    }

    @DeleteMapping("/delete-image/{uuid}")
    @ApiOperation("删除图片")
    public Response<Boolean> deleteImage(@PathVariable("uuid") String uuid){
        return this.ok(this.adminImageServices.deleteImage(uuid));
    }

    @GetMapping(value = "/get/{uuid}")
    @ApiOperation("获取背景图片")
    public ResponseEntity<byte[]> getImageByName(@PathVariable("uuid") String uuid) throws IOException {
        byte[] bytes = this.adminImageServices.getImage(uuid);
        return this.ok(bytes, MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
    }

    @GetMapping("/get-admin-images")
    @ApiOperation("筛选菜品")
    public Response<List<AdminImage>> getByParams(@RequestParam("code") String code, @RequestParam("name") String name, @RequestParam("status") String status){
        return this.ok(this.adminImageServices.getByParams(code,name,status));
    }
}

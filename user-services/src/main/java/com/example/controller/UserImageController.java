package com.example.controller;

import com.example.entity.AdminImage;
import com.example.response.Response;
import com.example.services.UserImageServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user-image")
@Api(tags = "用户获取图片模块", description = "用户获取图片模块")
public class UserImageController extends BaseController {

    @Resource
    private UserImageServices userImageServices;

    @GetMapping("/image")
    @ApiOperation("获取背景图片")
    public Response<List<Map<String, String>>> getImage(@RequestParam("code") String code) throws IOException {
        List<Map<String, String>> images = this.userImageServices.getUserImage(code);
        return this.ok(images);
    }

    @GetMapping(value = "/{uuid}")
    @ApiOperation("获取背景图片")
    public ResponseEntity<byte[]> getImageByName(@PathVariable("uuid") String uuid) throws IOException {
        byte[] bytes = this.userImageServices.getImageByName(uuid);
        return this.ok(bytes, MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
    }
    @GetMapping("/get/{code}/{name}")
    @ApiOperation("获取几星推荐图片")
    public ResponseEntity<byte[]> getByNameAndCode(@PathVariable("code") String code, @PathVariable("name") String name) throws IOException {
        byte[] bytes = this.userImageServices.getByNameAndCode(name,code);
        return this.ok(bytes, MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
    }
}

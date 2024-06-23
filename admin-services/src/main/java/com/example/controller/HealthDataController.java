package com.example.controller;

import com.example.entity.Dish;
import com.example.entity.HealthData;
import com.example.response.Response;
import com.example.services.HealthDataServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin-health-data")
@Api(tags = "数据模块", description = "数据模块")
public class HealthDataController extends BaseController {
    @Resource
    private HealthDataServices healthDataServices;

    @GetMapping("/health-data-get")
    @ApiOperation("获取全部菜品")
    public Response<List<HealthData>> getAll(@RequestParam("username") String username) {
        return this.ok(this.healthDataServices.getAll(username));
    }
    @PostMapping("/health-data-update")
    @ApiOperation("获取全部菜品")
    public Response<Boolean> update(@RequestBody HealthData healthData) {
        return this.ok(this.healthDataServices.update(healthData));
    }
    @DeleteMapping("/health-data-delete/{id}")
    @ApiOperation("获取全部菜品")
    public Response<Boolean> delete(@PathVariable("id") int id) {
        return this.ok(this.healthDataServices.delete(id));
    }

    @PostMapping(value = "/uploadFile")
    @ApiOperation("上传文件")
    public Response<Boolean> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return this.ok(this.healthDataServices.importData(file));
    }
}

package com.example.controller;


import com.example.entity.Activity;
import com.example.response.Response;
import com.example.services.ActivityServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user-activity")
@Api(tags = "活动模块", description = "活动模块")
public class ActivityController extends BaseController {

    @Resource
    private ActivityServices activityServices;


    @GetMapping("/get-activity")
    @ApiOperation("获取活动")
    public Response<List<Map<String, Object>>> getAll() {
        return this.ok(this.activityServices.getAll());
    }
    @GetMapping("/sign-activity")
    @ApiOperation("活动报名")
    public Response<Boolean> sign(@RequestParam("userId") int userId, @RequestParam("id") int id){
        return this.ok(this.activityServices.sign(userId, id));
    }
}

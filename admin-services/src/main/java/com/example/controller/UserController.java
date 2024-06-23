package com.example.controller;

import com.example.entity.Family;
import com.example.entity.UserInfo;
import com.example.response.Response;
import com.example.services.UserInfoServices;
import com.example.vo.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin-user")
@Api(tags = "用户管理模块", description = "用户管理模块")
public class UserController extends BaseController {

    @Resource
    private UserInfoServices userInfoServices;

    @PostMapping("/admin-user-get")
    @ApiOperation("获取全部用户")
    public Response<List<UserInfo>> getAll(@RequestBody UserInfoVo vo) {
        return this.ok(this.userInfoServices.getUsers(vo));
    }

    @GetMapping("/admin-family")
    @ApiOperation("获取用户的家人")
    public Response<List<Family>> getAll(@RequestParam("id") int id) {
        return this.ok(this.userInfoServices.getFamily(id));
    }
}

package com.example.controller;

import com.example.baseEntity.LoginMsgVo;
import com.example.entity.Dish;
import com.example.entity.Family;
import com.example.entity.UserInfo;
import com.example.response.ErrorMsg;
import com.example.response.Response;
import com.example.services.FamilyServices;
import com.example.services.UserServices;
import com.example.vo.UserInfoVo;
import com.example.vo.WxInfoVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户模块", description = "用户模块")
public class UserController extends BaseController {

    @Resource
    private UserServices userServices;
    @Resource
    private FamilyServices familyServices;

    @GetMapping("/information")
    @ApiOperation("用户资料完善")
    public Response<Boolean> perfectInfo(@RequestParam("user") String user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfo userInfo = objectMapper.readValue(user, UserInfo.class);
        Boolean res = this.userServices.perfectInfo(userInfo);
        return this.ok(res);
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Response<UserInfo> Login(@RequestBody WxInfoVo vo) {
        return this.ok(this.userServices.login(vo));
    }

    @GetMapping("/logout")
    @ApiOperation("用户登录")
    public Response<Boolean> logout(@RequestParam("username") String username) {
        return this.ok(this.userServices.Logout(username));
    }

    @PostMapping("/insert")
    @ApiOperation("添加亲友")
    public Response<Boolean> addFamily(@RequestParam("family") String family,@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Family fa = objectMapper.readValue(family, Family.class);
        Boolean res = this.familyServices.inert(fa,file);
        return this.ok(res);
    }

    @PostMapping("update-family")
    @ApiOperation("修改亲友信息")
    public Response<Boolean> updateFamily(@RequestBody Family family) {
        Boolean res = this.familyServices.update(family);
        return this.ok(res);
    }

    @GetMapping("/get-info")
    @ApiOperation("获取用户信息")
    public Response<UserInfoVo> getUserInfos(@RequestParam("userId") int userId) {
        return this.ok(this.userServices.getUserInfos(userId));
    }

    @GetMapping("/get-family")
    @ApiOperation("获取用户的家人")
    public Response<List<Family>> getAll(@RequestParam("id") int id) {
        return this.ok(this.userServices.getFamily(id));
    }

    @GetMapping("/get-user-list")
    @ApiOperation("获取用户")
    public Response<List<UserInfo>> getAllUser(@RequestParam("id") int id) {
        return this.ok(this.userServices.getUserList(id));
    }

}

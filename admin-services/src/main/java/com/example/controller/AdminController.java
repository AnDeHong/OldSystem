package com.example.controller;

import com.example.baseEntity.LoginMsgVo;
import com.example.entity.Admin;
import com.example.response.ErrorMsg;
import com.example.response.Response;
import com.example.services.AdminServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员模块", description = "管理员模块")
public class AdminController extends BaseController {

    @Resource
    private AdminServices adminServices;



    @PostMapping("/admin-login")
    @ApiOperation("管理员登录")
    public Response<Map<String, String>> userLogin(@RequestBody LoginMsgVo vo) {
        Map<String, String> res = this.adminServices.Login(vo);
        if (res == null) {
            Map<String, String> result = new HashMap<>();
            result.put("res", ErrorMsg.NO_DATA.getError());
            return this.error(result);
        }
        return this.ok(res);
    }

    @GetMapping("/admin-logout")
    @ApiOperation("退出登录")
    public Response<Boolean> Logout(String username) {
        return this.ok(this.adminServices.Logout(username));
    }

    @GetMapping("/admin-get")
    @ApiOperation("获取全部系统管理员")
    public Response<List<Admin>> getAll() {
        return this.ok(this.adminServices.getAll());
    }
    @PostMapping("/admin-insert")
    @ApiOperation("添加系统管理员")
    public Response<Boolean> insert(@RequestBody Admin admin) {
        return this.ok(this.adminServices.insert(admin));
    }

    @DeleteMapping("/admin-delete/{id}")
    @ApiOperation("根据id删除系统管理员")
    public Response<Boolean> delete(@PathVariable("id") int id) {
        return this.ok(this.adminServices.delete(id));
    }

    @GetMapping("/admin-get-name")
    @ApiOperation("根据用户名获取系统管理员")
    public Response<List<Admin>> findByName(@RequestParam("name") String name) {
        return this.ok(this.adminServices.findByName(name));
    }
    @PostMapping("/admin-update")
    @ApiOperation("修改系统管理员")
    public Response<Boolean> update(@RequestBody Admin admin) {
        return this.ok(this.adminServices.update(admin));
    }
}

package com.example.controller;

import com.example.common.vo.DictTypeVo;
import com.example.entity.Dish;
import com.example.entity.Notice;
import com.example.response.Response;
import com.example.services.DictServices;
import com.example.services.NoticesServices;
import com.example.vo.NoticeVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin-notice")
@Api(tags = "公告模块", description = "公告模块")
public class NoticesController extends BaseController{

    @Resource
    private DictServices dictServices;
    @Resource
    private NoticesServices noticesServices;

    @PostMapping("/get-notice-get")
    @ApiOperation("获取公告")
    public Response<List<Notice>> getAll(@RequestBody NoticeVo vo){
        return this.ok(this.noticesServices.getAll(vo));
    }

    @GetMapping("/get-notice-type")
    @ApiOperation("获取公告的类型")
    public Response<String[]> getNoticeType(){
        return this.ok(this.dictServices.getValues(String.valueOf(DictTypeVo.NOTICE)));
    }
    @PostMapping("/get-notice-insert")
    @ApiOperation("添加公告")
    public Response<Boolean> insert(@RequestBody NoticeVo vo){
        return this.ok(this.noticesServices.insert(vo));
    }

    @PostMapping("/get-notice-update")
    @ApiOperation("修改公告")
    public Response<Boolean> update(@RequestBody Notice vo){
        return this.ok(this.noticesServices.update(vo));
    }

    @DeleteMapping("/get-notice-delete/{id}")
    @ApiOperation("删除公告")
    public Response<Boolean> delete(@PathVariable("id") int id){
        return this.ok(this.noticesServices.delete(id));
    }
}

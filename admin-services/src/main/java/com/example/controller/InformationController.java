package com.example.controller;

import com.example.common.vo.DictTypeVo;
import com.example.entity.Activity;
import com.example.entity.Dish;
import com.example.entity.Information;
import com.example.mapper.InformationMapper;
import com.example.response.Response;
import com.example.services.DictServices;
import com.example.services.InformationServices;
import com.example.vo.ActivityVo;
import com.example.vo.InforQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin-information")
@Api(tags = "资讯模块", description = "资讯模块")
public class InformationController extends BaseController{

    @Resource
    private DictServices dictServices;
    @Resource
    private InformationServices informationServices;

    @GetMapping("/get-information-type")
    @ApiOperation("获取资讯文章的类型")
    public Response<String[]> getNoticeType(@RequestParam("type") String type){
        return this.ok(this.dictServices.getValues(type));
    }
    @PostMapping("/get-information")
    @ApiOperation("获取资讯文章")
    public Response<List<Information>> getAll(@RequestBody InforQueryVo vo) {
        return this.ok(this.informationServices.getAll(vo));
    }

    @PostMapping("/information-insert")
    @ApiOperation("添加资讯文章")
    public Response<Boolean> insert(@RequestBody Information vo) {
        return this.ok(this.informationServices.insert(vo));
    }

    @PostMapping("/information-update")
    @ApiOperation("修改活动")
    public Response<Boolean> update(@RequestBody Information information){
        return this.ok(this.informationServices.update(information));
    }
    @DeleteMapping("/information-delete/{id}")
    @ApiOperation("删除活动")
    public Response<Boolean> delete(@PathVariable("id") int id){
        return this.ok(this.informationServices.delete(id));
    }

    @PostMapping("/information-update-status")
    @ApiOperation("修改咨资讯文章是否上架的状态")
    public Response<Boolean> updateStatus(@RequestBody Information information){
        return this.ok(this.informationServices.updateStatus(information));
    }
}

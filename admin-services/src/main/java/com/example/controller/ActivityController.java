package com.example.controller;


import com.example.common.vo.DictTypeVo;
import com.example.entity.Activity;
import com.example.response.Response;
import com.example.services.ActivityServices;
import com.example.services.DictServices;
import com.example.vo.ActivityVo;
import com.example.vo.NoticeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin-activity")
@Api(tags = "活动模块", description = "活动模块")
public class ActivityController extends BaseController {

    @Resource
    private DictServices dictServices;
    @Resource
    private ActivityServices activityServices;

    @GetMapping("/get-activity-type")
    @ApiOperation("获取活动的类型")
    public Response<String[]> getNoticeType(){
        return this.ok(this.dictServices.getValues(String.valueOf(DictTypeVo.ACTIVITY)));
    }

    @PostMapping("/get-activity-insert")
    @ApiOperation("添加活动")
    public Response<Boolean> insert(@RequestBody ActivityVo vo) throws ParseException {
        return this.ok(this.activityServices.insert(vo));
    }

    @PostMapping("/get-activity-get")
    @ApiOperation("获取活动")
    public Response<List<Activity>> getAll(@RequestBody ActivityVo vo) {
        return this.ok(this.activityServices.getAll(vo));
    }
    @PostMapping("/get-activity-update")
    @ApiOperation("修改活动")
    public Response<Boolean> update(@RequestBody Activity activity){
        return this.ok(this.activityServices.update(activity));
    }
    @DeleteMapping("/get-activity-delete/{id}")
    @ApiOperation("删除活动")
    public Response<Boolean> delete(@PathVariable("id") int id){
        return this.ok(this.activityServices.delete(id));
    }
}

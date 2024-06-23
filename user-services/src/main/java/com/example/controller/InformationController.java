package com.example.controller;

import com.example.entity.Information;
import com.example.entity.Notice;
import com.example.response.Response;
import com.example.services.InformationServices;
import com.example.vo.InformationQueryVo;
import com.example.vo.informationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user-information")
@Api(tags = "科普模块", description = "科普模块")
public class InformationController extends BaseController {

    @Resource
    private InformationServices informationServices;

    @PostMapping("/get-information")
    @ApiOperation("获取科普")
    public Response<List<informationVo>> getAll(@RequestBody InformationQueryVo vo){
        return this.ok(this.informationServices.getInformation(vo));
    }

    @GetMapping("/update-read-times")
    @ApiOperation("更新科普阅读次数")
    public Response<Boolean> updateReadTimes(@RequestParam("id") int id){
        return this.ok(this.informationServices.updateReadTimes(id));
    }
}

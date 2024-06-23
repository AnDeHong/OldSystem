package com.example.controller;

import com.example.entity.OperationLog;
import com.example.mapper.OperationLogMapper;
import com.example.response.Response;
import com.example.services.LogServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin-log")
@Api(tags = "日志模块", description = "日志模块")
public class LogController extends BaseController {

    @Resource
    private LogServices logServices;

    @GetMapping("/log-getAll")
    @ApiOperation("获取全部日志")
    public Response<List<OperationLog>> getAll() {
        return this.ok(this.logServices.getAll());
    }
}

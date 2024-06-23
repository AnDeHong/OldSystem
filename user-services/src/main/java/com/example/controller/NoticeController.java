package com.example.controller;

import com.example.entity.Notice;
import com.example.response.Response;
import com.example.services.NoticeServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user-notice")
@Api(tags = "公告模块", description = "公告模块")
public class NoticeController extends BaseController {

    @Resource
    private NoticeServices noticeServices;

    @GetMapping("/get-notice")
    @ApiOperation("获取公告")
    public Response<List<Notice>> getAll(){
        return this.ok(this.noticeServices.getAllList());
    }

    @GetMapping("/get-last-notice")
    @ApiOperation("获取公告")
    public Response<Notice> getLast(){
        return this.ok(this.noticeServices.getLast());
    }
}

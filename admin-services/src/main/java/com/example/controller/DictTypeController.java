package com.example.controller;

import com.example.entity.Dict;
import com.example.entity.DictType;
import com.example.services.DictServices;
import com.example.services.DictTypeServices;
import com.example.response.Response;
import com.example.vo.DictTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin-dict")
@Api(tags = "字典项模块", description = "字典项模块")
public class DictTypeController extends BaseController {
    @Resource
    private DictServices dictServices;

    @Resource
    private DictTypeServices dictTypeServices;

    @PostMapping("/dict-add")
    @ApiOperation("添加字典项配置")
    public Response<Boolean> insert(@RequestBody Dict dict) {
        return this.ok(this.dictServices.insert(dict));
    }
    @GetMapping("/dict-type")
    @ApiOperation("获取所有字典项配置")
    public Response<List<Dict>> getDictType() {
        return this.ok(this.dictServices.getDictType());
    }

    @GetMapping("/dict-code")
    @ApiOperation("获取所有字典项配置的所有code")
    public Response<String[]> getDictCode() {
        return this.ok(this.dictServices.getDictCode());
    }

    @GetMapping("/dict-type-name")
    @ApiOperation("获取所有字典项配置的name")
    public Response<List<DictTypeVo>> getDictName(@RequestParam("code") String code) {
        return this.ok(this.dictServices.getDictName(code));
    }

    @GetMapping("/dict-type-dict")
    @ApiOperation("获取该字典项配置下的所有详情配置的value")
    public Response<String[]> getValues(@RequestParam("code") String code) {
        return this.ok(this.dictServices.getValues(code));
    }

    @PostMapping("/dict-type-insert")
    @ApiOperation("添加该字典项配置下的详情配置")
    public Response<Boolean> insertType(@RequestBody DictType dictType) {
        return this.ok(this.dictTypeServices.insert(dictType));
    }

    @PostMapping("/dict-type-update")
    @ApiOperation("修改该字典项配置下的详情配置")
    public Response<Boolean> updateType(@RequestBody DictType dictType) {
        return this.ok(this.dictTypeServices.update(dictType));
    }

    @DeleteMapping("/dict-type-delete/{id}")
    @ApiOperation("删除该字典项配置下的详情配置")
    public Response<Boolean> deleteType(@PathVariable("id") int id) {
        return this.ok(this.dictTypeServices.delete(id));
    }
    @GetMapping("/dict-type-get")
    @ApiOperation("获取该字典项配置下的所有详情配置")
    public Response<List<DictType>> getAllType(@RequestParam("code") String code) {
        return this.ok(this.dictTypeServices.getAllType(code));
    }
}

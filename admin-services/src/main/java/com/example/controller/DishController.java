package com.example.controller;

import com.example.common.vo.DictTypeVo;
import com.example.entity.AdminImage;
import com.example.entity.Dish;
import com.example.entity.Package;
import com.example.response.Response;
import com.example.services.DictServices;
import com.example.services.DishServices;
import com.example.services.PackageServices;
import com.example.vo.DishVo;
import com.example.vo.PackageVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin-dish")
@Api(tags = "菜品模块", description = "菜品模块")
public class DishController extends BaseController {

    @Resource
    private DishServices dishServices;
    @Resource
    public DictServices dictServices;
    @Resource
    private PackageServices packageServices;

    @GetMapping("/dish-getAll")
    @ApiOperation("获取全部菜品")
    public Response<List<Dish>> getAll() {
        return this.ok(this.dishServices.getAll());
    }

    @PostMapping("/dish-update")
    @ApiOperation("修改菜品")
    public Response<Boolean> update(@RequestParam("dish") String dishJson, @RequestParam(value = "fileUpload",required = false) MultipartFile fileUpload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Dish dish = objectMapper.readValue(dishJson, Dish.class);
        return this.ok(this.dishServices.update(dish,fileUpload));
    }

    @PostMapping("/dish-insert")
    @ApiOperation("添加菜品")
    public Response<Boolean> insert(@RequestParam("dish") String dishJson,@RequestParam("fileUpload") MultipartFile fileUpload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DishVo dish = objectMapper.readValue(dishJson, DishVo.class);
        return this.ok(this.dishServices.insert(dish,fileUpload));
    }
    @DeleteMapping("/dish-delete/{id}")
    @ApiOperation("删除菜品")
    public Response<Boolean> delete(@PathVariable("id") int id) {
        return this.ok(this.dishServices.delete(id));
    }

    @GetMapping("/dish-type")
    @ApiOperation("通过类型获取菜品")
    public Response<List<Dish>> getByType(@RequestParam("type") String type) {
        return this.ok(this.dishServices.getByType(type));
    }

    @PostMapping("/dish-update-status")
    @ApiOperation("修改菜品是否上架的状态")
    public Response<Boolean> updateStatus(@RequestBody Dish dish){
        return this.ok(this.dishServices.updateStatus(dish));
    }

    @GetMapping("/get-dish-values")
    @ApiOperation("获取图片的value值")
    public Response<String[]> getDishValues(){
        return this.ok(this.dictServices.getValues(String.valueOf(DictTypeVo.DISHIMG)));
    }

    @GetMapping("/get-dishes")
    @ApiOperation("筛选菜品")
    public Response<List<Dish>> getDishes(@RequestParam("name") String name, @RequestParam("type") String type,@RequestParam("status") String status){
        return this.ok(this.dishServices.getDishes(name,type,status));
    }

    @GetMapping("/get-dish-name")
    @ApiOperation("获取菜品名称和id")
    public Response<List<Map<String, String>>> getDishName(@RequestParam("type") String type){
        return this.ok(this.dishServices.getDishName(type));
    }

    @GetMapping("/package-getAll")
    @ApiOperation("获取全部套餐")
    public Response<List<PackageVo>> getAllPackage() {
        return this.ok(this.packageServices.getAll());
    }

    @PostMapping("/package-update")
    @ApiOperation("修改套餐")
    public Response<Boolean> updatePackage(@RequestParam("packages") String packages, @RequestParam(value = "fileUpload",required = false)MultipartFile fileUpload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Package apackage = objectMapper.readValue(packages, Package.class);
        return this.ok(this.packageServices.update(apackage,fileUpload));
    }

    @PostMapping("/package-insert")
    @ApiOperation("添加套餐")
    public Response<Boolean> insertPackage(@RequestParam("packages") String packages,@RequestParam(value = "fileUpload") MultipartFile fileUpload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Package apackage = objectMapper.readValue(packages, Package.class);
        return this.ok(this.packageServices.insert(apackage,fileUpload));
    }
    @DeleteMapping("/package-delete/{id}")
    @ApiOperation("删除套餐")
    public Response<Boolean> deletePackage(@PathVariable("id") int id) {
        return this.ok(this.packageServices.delete(id));
    }

    @PostMapping("/package-update-status")
    @ApiOperation("修改套餐是否上架的状态")
    public Response<Boolean> updateStatus(@RequestBody Package dish){
        return this.ok(this.packageServices.updateStatus(dish));
    }

    @GetMapping("/get-package-status")
    @ApiOperation("筛选套餐")
    public Response<List<PackageVo>> getByStatus(@RequestParam(value = "name",required = false) String name, @RequestParam(value = "type",required = false) String type,@RequestParam(value = "status",required = false) String status){
        return this.ok(this.packageServices.getByStatus(name,type,status));
    }
}

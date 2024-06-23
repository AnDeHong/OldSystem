package com.example.controller;

import com.example.entity.Address;
import com.example.entity.Dish;
import com.example.entity.DongTai;
import com.example.response.Response;
import com.example.services.AddressServices;
import com.example.services.OrdersServices;
import com.example.services.PackageServices;
import com.example.services.UserDishServices;
import com.example.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/user-dish")
@Api(value = "菜品模块", description = "菜品模块")
public class UserDishController extends BaseController {

    @Resource
    private UserDishServices userDishServices;
    @Resource
    private PackageServices packageServices;
    @Resource
    private OrdersServices ordersServices;
    @Resource
    private AddressServices addressServices;

    @GetMapping("/get")
    @ApiOperation("获取全部菜品")
    public Response<List<DishVo>> getAll() {
        List<DishVo> dishes = this.userDishServices.getAll();
        return this.ok(dishes);
    }


    @GetMapping("/get-package")
    @ApiOperation("获取优选套餐")
    public Response<List<PackageVo>> getPackage(@RequestParam("tag") String tag) {
        List<PackageVo> list = this.packageServices.getAll(tag);
        return this.ok(list);
    }

    @PostMapping("/order-add")
    @ApiOperation("用户点餐")
    public Response<Boolean> orderStart(@RequestBody AddOrdersVo vo) throws ParseException {
        return this.ok(this.ordersServices.insert(vo));
    }

    @PostMapping("/order-package")
    @ApiOperation("用户套餐点餐")
    public Response<Boolean> addPackage(@RequestBody AddPackageVo vo) {
        return this.ok(this.ordersServices.insertOrders(vo));
    }

    @GetMapping("/get-orders")
    @ApiOperation("获取用户订单")
    public Response<List<OrdersVo>> getOrders(@RequestParam("userId") int userId) {
        List<OrdersVo> list = this.ordersServices.getUserOrders(userId);
        return this.ok(list);
    }
    @GetMapping("/order-evaluate")
    @ApiOperation("用户评价")
    public Response<Boolean> evaluateOrder(@RequestParam("id") int id,@RequestParam("evaluate") String evaluate) {
        return this.ok(this.ordersServices.updateEvaluate(id, evaluate));
    }

    @GetMapping("/order-choose-date")
    @ApiOperation("通过预定时间获取订单")
    public Response<List<OrdersVo>> getOrdersByDate(@RequestParam("time") String time,@RequestParam("time1") String time1) {
        return this.ok(this.ordersServices.getOrdersByDate(time,time1));
    }

    @GetMapping("/get-address")
    @ApiOperation("获取地址")
    public Response<List<Address>> getAddress(@RequestParam("userId") int userId) {
        return this.ok(this.addressServices.getAddress(userId));
    }

    @PostMapping("/add-address")
    @ApiOperation("添加地址")
    public Response<Boolean> insertAddress(@RequestBody Address address) {
        return this.ok(this.addressServices.insert(address));
    }

    @PostMapping("/update-address")
    @ApiOperation("修改地址")
    public Response<Boolean> updateAddress(@RequestBody Address address) {
        return this.ok(this.addressServices.update(address));
    }

    @DeleteMapping("/delete-address/{id}")
    @ApiOperation("删除地址")
    public Response<Boolean> deleteAddress(@PathVariable("id") int id) {
        return this.ok(this.addressServices.delete(id));
    }

    @GetMapping("/update-dongtai")
    @ApiOperation("获取动态")
    public Response<List<OrdersVo>> updateAddress(@RequestParam("userId") int userId) {
        return this.ok(this.ordersServices.getNewOrder(userId));
    }

    @GetMapping("/get-dongtai")
    @ApiOperation("修改动态数量显示")
    public Response<Boolean> getDongTai(@RequestParam("userId") int userId) {
        return this.ok(this.ordersServices.getDongTai(userId));
    }

    @GetMapping("/get-dongtaiNum")
    @ApiOperation("修改动态数量显示")
    public Response<DongTai> getDongTaiNum(@RequestParam("userId") int userId) {
        return this.ok(this.ordersServices.getDongTaiNum(userId));
    }

    @GetMapping("/update-user_address")
    @ApiOperation("修改用户地址")
    public Response<Boolean> updateUserAddress(@RequestParam("userId") int userId,@RequestParam("id") int id) {
        return this.ok(this.ordersServices.updateUserAddress(userId,id));
    }
}

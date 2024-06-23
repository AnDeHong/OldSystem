package com.example.controller;

import com.example.common.vo.DictTypeVo;
import com.example.entity.Dish;
import com.example.entity.DishOrder;
import com.example.entity.OperationLog;
import com.example.entity.Orders;
import com.example.response.Response;
import com.example.services.DictServices;
import com.example.services.OrdersServices;
import com.example.vo.AddDishOrderVo;
import com.example.vo.OrderGetVo;
import com.example.vo.OrdersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin-orders")
@Api(tags = "订单模块", description = "订单模块")
public class OrdersController extends BaseController {
    @Resource
    private OrdersServices ordersServices;
    @Resource
    public DictServices dictServices;

    @GetMapping("/get")
    @ApiOperation("获取全部订单")
    public Response<List<OrdersVo>> getAll() {
        return this.ok(this.ordersServices.getOrders());
    }

    @GetMapping("/get-order-status")
    @ApiOperation("获取订单状态")
    public Response<String[]> getOrderStatus(){
        return this.ok(this.dictServices.getValues(String.valueOf(DictTypeVo.ORDER)));
    }

    @PostMapping("/get-order-by")
    @ApiOperation("条件查询订单")
    public Response<List<OrdersVo>> getOrderBy(@RequestBody OrderGetVo vo) throws ParseException {
        return this.ok(this.ordersServices.getOrderBy(vo));
    }

    @DeleteMapping("/delete-order/{id}")
    @ApiOperation("删除订单")
    public Response<Boolean> delete(@PathVariable("id") int id) {
        return this.ok(this.ordersServices.delete(id));
    }

    @PostMapping("/update-order-status")
    @ApiOperation("修改订单状态")
    public Response<Boolean> updateStatus(@RequestBody Orders orders) {
        return this.ok(this.ordersServices.updateStatus(orders));
    }

    @PostMapping("/update-order-dish")
    @ApiOperation("修改订单中的菜品")
    public Response<Boolean> update(@RequestBody DishOrder dishOrder) {
        return this.ok(this.ordersServices.update(dishOrder));
    }

    @PostMapping("/delete-order-dish")
    @ApiOperation("删除订单菜品")
    public Response<Boolean> deleteDishOrder(@RequestBody DishOrder dishOrder) {
        return this.ok(this.ordersServices.deleteDishOrder(dishOrder));
    }

    @GetMapping("/get-dish-by-name")
    @ApiOperation("通过名称获取菜品")
    public Response<List<Dish>> getDishesByName(@RequestParam("name") String name) {
        return this.ok(this.ordersServices.getDishesByName(name));
    }

    @PostMapping("/add-order-dish")
    @ApiOperation("添加订单菜品")
    public Response<Boolean> addOrderDish(@RequestBody AddDishOrderVo vo) {
        return this.ok(this.ordersServices.insert(vo));
    }
}

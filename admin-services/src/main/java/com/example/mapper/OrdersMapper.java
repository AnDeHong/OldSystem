package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Dish;
import com.example.entity.DishOrder;
import com.example.entity.Orders;
import com.example.vo.OrderGetVo;
import org.apache.ibatis.annotations.Param;
import com.example.vo.OrdersVo;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    List<OrdersVo> getOrders();

    List<OrdersVo> getOrdersBy(OrderGetVo vo);

    @Select("select * from orders where delete_mark = 'N' and id = #{id}")
    Orders getById(int id);
    @Select("select * from dish where delete_mark = 'N' and is_show = '上架' and dish_name like \"%\"#{name}\"%\"")
    List<Dish> getByDishName(String name);

    DishOrder getDishOrderById(@Param("id") int id);
}

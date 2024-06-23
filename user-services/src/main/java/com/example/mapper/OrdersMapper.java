package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Orders;
import com.example.vo.OrdersVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    List<OrdersVo> getOrders(@Param("userId") int userId);

    List<OrdersVo> getOrdersByDate(@Param("time") String time,@Param("time1") String time1);

    List<OrdersVo> getAllOrder();
}

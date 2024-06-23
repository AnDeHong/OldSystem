package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Dish;
import com.example.entity.DishOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DishOrderMapper extends BaseMapper<DishOrder> {

    DishOrder getPackDishByNo(@Param("dishNo") String dishNo);
}

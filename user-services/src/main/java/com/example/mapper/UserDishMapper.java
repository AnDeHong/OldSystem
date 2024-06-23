package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Dish;
import com.example.entity.DishOrder;
import com.example.vo.DishVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDishMapper extends BaseMapper<Dish> {

    List<DishVo> getAll();
    Dish getPackDish(@Param("name") String name);

}

package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Dish;
import com.example.vo.DishVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

//    @Select("select * from dish where delete_mark = 'N'")
    List<Dish> getAll();

    @Select("select * from dish where type = #{type} and delete_mark = 'N'")
    List<Dish> getByType(String type);

    List<Dish> getDishes(@Param("name") String name,@Param("type") String type,@Param("status") String status);

    List<DishVo> getDishName(@Param("type") String type);

    Dish getPackDish(@Param("name") String name);

        Dish getPackDishByNo(@Param("packageNo") String packageNo);

}

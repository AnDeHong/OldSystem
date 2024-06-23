package com.example.services.IServices;

import com.example.entity.Dish;
import com.example.entity.DishOrder;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

public interface IDishServices {

    List<Dish> getByIds(String[] ids);

    List<DishOrder> getById(String[] ids);

}

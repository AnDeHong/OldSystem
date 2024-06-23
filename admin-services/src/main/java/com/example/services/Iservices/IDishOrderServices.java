package com.example.services.Iservices;

import com.example.entity.Dish;
import com.example.entity.DishOrder;

import java.util.List;

public interface IDishOrderServices {
    List<DishOrder> getById(String[] ids);

    /**
     * 获取删除订单中一道菜品后订单中剩余的菜品id
     * @param ids
     * @return
     */
    String getDishOrderIds(String ids, int id);
}

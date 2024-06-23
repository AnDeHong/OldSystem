package com.example.services;

import com.example.entity.Dish;
import com.example.entity.DishOrder;
import com.example.mapper.DishOrderMapper;
import com.example.services.Iservices.IDishOrderServices;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DishOrderServices implements IDishOrderServices {
    @Resource
    private DishOrderMapper dishOrderMapper;

    @Override
    public List<DishOrder> getById(String[] ids) {
        if (ids.length == 0) {
            return null;
        }
        List<DishOrder> dishList = new ArrayList<>();
        for (String i : ids) {
            if (!Objects.equals(i, "")) {
                DishOrder dish = this.dishOrderMapper.getPackDishByNo(i);
                dishList.add(dish);
            }
        }
        return dishList;
    }

    @Override
    public String getDishOrderIds(String ids, int id) {
        return ids.replace(String.valueOf(id),"");
    }
}

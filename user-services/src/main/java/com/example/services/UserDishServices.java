package com.example.services;

import com.example.entity.Dish;
import com.example.entity.DishOrder;
import com.example.mapper.DishOrderMapper;
import com.example.mapper.UserDishMapper;
import com.example.services.IServices.IDishServices;
import com.example.vo.DishVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDishServices implements IDishServices {

    @Resource
    private UserDishMapper userDishMapper;
    @Resource
    private DishOrderMapper dishOrderMapper;

    public List<DishVo> getAll() {
        List<DishVo> dishVos = this.userDishMapper.getAll();
        if (dishVos.size() > 0) {
            for (DishVo dishVo: dishVos) {
                String url = String.format("http://localhost:8808/user-services/user-image/get/recommendation/%d",dishVo.getRecommendationLevel());
                dishVo.setRecommendUrl(url);
                String[] tags = dishVo.getTag().split(",");
                List<String> tagList = new ArrayList<>();
                tagList.addAll(Arrays.asList(tags));
                dishVo.setTags(tagList);
            }
            return dishVos;
        }
        return null;
    }

    @Override
    public List<Dish> getByIds(String[] ids) {
        if (ids.length == 0) {
            return null;
        }
        List<Dish> dishList = new ArrayList<>();
        for (String i : ids) {
            Dish dish = this.userDishMapper.getPackDish(i);
            dishList.add(dish);
        }
        return dishList;
    }
    @Override
    public List<DishOrder> getById(String[] ids) {
        if (ids.length == 0) {
            return null;
        }
        List<DishOrder> dishList = new ArrayList<>();
        for (String i : ids) {
            if (!Objects.equals(i, "")) {
                DishOrder dish = this.dishOrderMapper.getPackDishById(i);
                dishList.add(dish);
            }
        }
        return dishList;
    }
}

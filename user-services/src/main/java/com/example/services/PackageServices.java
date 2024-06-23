package com.example.services;

import com.example.entity.Dish;
import com.example.entity.Package;
import com.example.mapper.PackageMapper;
import com.example.services.IServices.IDishServices;
import com.example.vo.PackageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PackageServices {
    @Resource
    private PackageMapper packageMapper;
    @Resource
    private IDishServices iDishServices;

    public List<PackageVo> getAll(String tag) {
        List<PackageVo> list = this.packageMapper.getAll(tag);
        if (list.size() > 0) {
            for (PackageVo item : list) {
                String[] ids = item.getDishIds().split(",");
                List<Dish> dishList = this.iDishServices.getByIds(ids);
                item.setDishes(dishList);
            }
        }
        return list;
    }
}

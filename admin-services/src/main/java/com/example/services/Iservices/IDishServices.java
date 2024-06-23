package com.example.services.Iservices;

import com.example.entity.Dish;

import java.io.IOException;
import java.util.List;

public interface IDishServices {

    String getImage(String uuid) throws IOException;

    String getUuid(String url);
    List<Dish> getByIds(String[] ids);
    List<Dish> getByNo(String[] ids);

    Boolean updateStatus(Dish dish);

    Float getAllPrice(String ids);

}

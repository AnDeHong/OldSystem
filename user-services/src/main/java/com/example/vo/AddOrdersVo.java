package com.example.vo;

import com.example.entity.Dish;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddOrdersVo {
    private Integer userId;

    private Integer addressId;

    private List<Dish> selectDish;

    private String notes;

    /** 预定时间 */
    private String chooseDate;
    private String startDate ;
    private String endDate ;
}

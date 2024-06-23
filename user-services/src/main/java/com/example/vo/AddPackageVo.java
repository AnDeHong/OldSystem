package com.example.vo;

import com.example.entity.Dish;
import lombok.Data;

import java.util.List;

@Data
public class AddPackageVo {
    private Integer userId;

    private Integer addressId;

    private String packageNo;
    private Integer num;

    private String notes;

    /** 预定时间 */
    private String chooseDate;
    private String startDate ;
    private String endDate ;
}

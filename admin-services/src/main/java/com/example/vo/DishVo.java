package com.example.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class DishVo {

    /** 菜品编号 */
    private String dishNo ;
    private int id;
    /** 菜品名称 */
    private String dishName ;
    /** 介绍 */
    private String introduce ;
    /** 推荐度 */
    private Integer recommendationLevel ;
    /** 类型：荤菜、素菜..... */
    private String type ;
    /** 表签：适合糖尿病人吃。。 */
    private String tag ;
    /** 原价格 */
    private Float price ;
    /** 折扣 */
    private Float discounts ;
    /** 折扣价 */
    private Float discountsPrice ;
    /** 是否售卖 */
    private String isShow ;
}

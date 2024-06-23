package com.example.vo;

import lombok.Data;

import java.util.List;

@Data
public class DishVo {

    /** 菜品编号 */
    private String dishNo ;
    private int id;
    /** 菜品名称 */
    private String dishName ;
    /** 图片 */
    private String imageUrl ;
    /** 类型：荤菜、素菜..... */
    private String type ;
    /** 表签：适合糖尿病人吃。。 */
    private String tag ;
    /** 原价格 */
    private Float price ;
    /** 折扣价 */
    private Float discountsPrice ;
    /** 单日销量 */
    private Integer total ;

    private Integer recommendationLevel;

    private List<String> tags ;

    private String recommendUrl;
}

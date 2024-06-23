package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;

@Data
@TableName("dish")
public class Dish extends BaseEntity {
    /** 菜品编号 */
    private String dishNo ;
    /** 菜品名称 */
    private String dishName ;
    /** 图片 */
    private String imageUrl ;
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
    /** 单日销量 */
    private Integer total ;

    /** 是否售卖 */
    private String isShow ;
}

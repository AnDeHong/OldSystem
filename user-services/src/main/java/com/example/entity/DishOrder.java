package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;

@Data
@TableName("dish_order")
public class DishOrder extends BaseEntity {

    /** 菜品编号 */
    private String dishNo ;
    /** 订单id */
    private Integer orderId ;
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
    @Column(precision = 10)
    private Float price ;
    /** 折扣 */
    @Column(precision = 10)
    private Float discounts ;
    /** 折扣价 */
    @Column(precision = 10)
    private Float discountsPrice ;
    /** 购买数量 */
    private Integer total ;
}

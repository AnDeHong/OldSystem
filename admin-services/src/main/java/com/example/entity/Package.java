package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
@TableName("package")
public class Package extends BaseEntity {
    private String packageNo;
    private String packageName;
    private String zhuShi;
    private String zhuCai;
    private String fuShi;
    private String fuCai;
    /** 菜品id集合 */
    private String dishIds ;
    /** 介绍 */
    private String introduce ;
    /** 图片 */
    private String imageUrl ;
    /** 标签 */
    private String tags ;
    /** 原价格 */
    @Column(precision = 10)
    private Float price ;
    /** 折扣 */
    @Column(precision = 10)
    private Float discount ;
    /** 现价 */
    @Column(precision = 10)
    private Float discountPrice ;
    /** 是否售卖 */
    private String isShow ;
    /** 销量 */
    private Integer total;
}

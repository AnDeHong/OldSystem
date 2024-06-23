package com.example.vo;

import com.example.entity.Dish;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class PackageVo {
    private int id;
    private String packageNo;
    private String zhuShi;
    private String zhuCai;
    private String fuShi;
    private String fuCai;

    private String packageName;
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
    /** 更新时间 */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updatedTime ;
    /** 是否售卖 */
    private String isShow ;
    /** 销量 */
    private Integer total;

    private List<Dish> dishes;
}

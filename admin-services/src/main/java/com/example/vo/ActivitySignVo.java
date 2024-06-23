package com.example.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ActivitySignVo {

    /** 活动id */
    private Integer activityId ;
    /**
     * 活动名称
     */
    private String name;
    /** 报名者id */
    private Integer userId ;
    /**
     * 用户名
     */
    private String username;
    private Integer age;
    private String sex;
    private String address;
    private String phone;
    /**
     * 慢性病标签
     */
    private String tag;
    /** 报名时间 */
    private Date signDate ;
    /** 男老人数量*/
    private Integer maleNum;
    /** 女老人数量*/
    private Integer womenNum;
}

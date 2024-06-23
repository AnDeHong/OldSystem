package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("family")
public class Family {
    /** id */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String avatarUrl;

    private Integer userId;

    private String name;

    private String relation;

    private Integer age;

    private String sex;

    private String address;

    private String phone;
}

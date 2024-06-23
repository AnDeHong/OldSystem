package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("interaction")
public class Quiz extends BaseEntity {
    /** 提问者id */
    private Integer userId ;
    /** 问题类容 */
    private String content ;
    /** 是否解答 */
    private String status ;
}

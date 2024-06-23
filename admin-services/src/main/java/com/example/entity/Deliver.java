package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;

@Data
@TableName("deliver")
public class Deliver extends BaseEntity {
    /** 配送员名字 */
    private String sendUser ;
    /** 电话 */
    private String phone ;
    /** 工作状态：上班，休息 */
    private String status ;
}

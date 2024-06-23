package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("activity_sign")
public class ActivitySign {
    /** id */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id ;

    /** 活动id */
    private Integer activityId ;
    /** 报名者id */
    private Integer userId ;
    /** 报名时间 */
    private Date signDate ;
}

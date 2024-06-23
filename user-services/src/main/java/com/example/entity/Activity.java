package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("activities")
public class Activity extends BaseEntity {

    /** 活动名称 */
    private String name ;
    /** 活动开始时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date startDate ;
    /** 活动结束时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date endDate ;
    /** 活动内容 */
    private String content ;
    /** 活动地址 */
    private String address ;
    /** 负责人*/
    private String head;
    /** 活动类别 */
    private String type ;
    /** 活动状态 */
    private String status ;
    /** 活动积分 */
    private String score ;
    /** 备注 */
    private String other ;
}

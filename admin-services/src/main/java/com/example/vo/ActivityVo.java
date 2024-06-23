package com.example.vo;

import lombok.Data;

@Data
public class ActivityVo {

    /** 活动名称 */
    private String name ;
    /** 活动开始时间 */
    private String startDate ;
    /** 活动结束时间 */
    private String endDate ;
    /** 活动内容 */
    private String content ;
    /** 活动地址 */
    private String address ;
    /** 活动状态 */
    private String head ;
    /** 活动类别 */
    private String type ;
    /** 活动状态 */
    private String status ;
    /** 活动积分 */
    private String score ;
    /** 备注 */
    private String other ;
    /**
     * 负责人
     */
    private String createBy;
}

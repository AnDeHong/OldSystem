package com.example.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeVo {
    /** 标题 */
    private String noticeTitle ;
    /** 公告内容 */
    private String noticeContent ;
    /** 公告类型 */
    private String type;
    /** 公告状态 */
    private String status;
    private String createBy;
}


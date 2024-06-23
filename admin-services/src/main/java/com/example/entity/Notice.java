package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;
import java.util.Date;

@Data
@TableName("notice")
public class Notice extends BaseEntity {
    /** 标题 */
    private String noticeTitle ;
    /** 公告内容 */
    private String noticeContent ;
    /** 公告类型 */
    private String type;
    /** 公告状态 */
    private String status;
}

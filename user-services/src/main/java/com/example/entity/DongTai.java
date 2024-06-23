package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("dongtai")
public class DongTai {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id ;
    /** 用户id */
    private Integer userId ;
    /** 动态条数 */
    private Integer newMsg ;
    /** 查看动态的最后时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date dateTime ;
}

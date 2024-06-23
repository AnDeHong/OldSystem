package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
@TableName("operationlog")
public class OperationLog {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id ;
    /** 用户名 */
    private String username ;
    /** 角色 */
    private String role ;
    /** 日期 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date time ;
    /** 操作 */
    private String operation;

    public OperationLog(){}
    public OperationLog(String username, String role, Date time, String operation) {
        this.username = username;
        this.role = role;
        this.time = time;
        this.operation = operation;
    }
}

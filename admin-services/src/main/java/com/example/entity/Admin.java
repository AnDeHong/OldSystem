package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin")
public class Admin {
    /** id */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id ;
    /** 账号 */
    private String username ;
    /** 密码 */
    private String password ;
    /** 权限 */
    private String role ;
}

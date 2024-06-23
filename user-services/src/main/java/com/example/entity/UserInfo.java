package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;

@Data
@TableName("userinfo")
public class UserInfo extends BaseEntity {
    /** 用户名 */
    private String username ;
    /** 头像 */
    private String avatarUrl;
    /** 邮箱 */
    private String email ;
    /** 电话 */
    private String phone ;
    /** openid */
    private String openid ;
    /** 年龄 */
    private Integer age ;
    /** 性别 */
    private String sex ;
    /** 地址 */
    private Integer addressId ;
    /** 总积分 */
    private int score ;
    /** 慢性病标签 */
    private String tag ;
}

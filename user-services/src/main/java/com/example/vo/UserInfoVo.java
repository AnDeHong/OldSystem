package com.example.vo;

import com.example.entity.Family;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoVo {
    private Integer id;
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
    private String address ;
    /** 总积分 */
    private int score ;
    /** 慢性病标签 */
    private String tag ;
    /** 亲友集合 */
    private List<Family> familyList;
}

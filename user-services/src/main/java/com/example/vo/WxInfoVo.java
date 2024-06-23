package com.example.vo;

import lombok.Data;

@Data
public class WxInfoVo {
    private String code;

    private String encryptedData;

    private String iv;

    private int sex;
    /** 头像 */
    private String avatarUrl;

    private String username;
}

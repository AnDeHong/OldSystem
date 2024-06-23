package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import com.example.utils.Const;
import com.example.utils.UserUtils;
import lombok.Data;

@Data
@TableName("admin_image")
public class AdminImage extends BaseEntity {

    /**
     * 上传图片的key值
     */
    private String code;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String value;
    /**
     * 链接
     */
    private String url;
    /**
     * 文本
     */
    private String text;
    /**
     * 是否显示
     */
    private String isShow;

    public AdminImage(String code, String uuid, String name, String value, String text,String url) {
        this.code = code;
        this.uuid = uuid;
        this.name = name;
        this.value = value;
        this.text = text;
        this.url = url;
        this.isShow = Const.NOSHOW;
    }
    public AdminImage(){}
}

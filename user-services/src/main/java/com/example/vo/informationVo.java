package com.example.vo;

import lombok.Data;

import java.util.List;

@Data
public class informationVo {
    private Integer id;
    /** 标题 */
    private String title ;
    /** 作者 */
    private String author ;
    /** 分类 */
    private String type ;
    /** 标签 */
    private String tag ;
    /** 发布时间 */
    private String releaseDate ;
    /** 内容 */
    private String content ;
    /** 文章链接 */
    private String url ;
    /** 图片链接 */
    private String imgUrl ;
    /** 状态 */
    private String status ;
    /** 阅读量 */
    private Integer readTimes ;

    private String[] imgUrls ;
}

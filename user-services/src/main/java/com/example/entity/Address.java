package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;

@Data
@TableName("address")
public class Address extends BaseEntity {

    /** 用户id */
    private Integer userId ;
    /** 所在公寓 */
    private String apartment ;
    /** 所在楼层 */
    private String floor ;
    /** 门牌号 */
    private String room ;
    private String checked;
    /** 门牌号 */
    private String phone ;
}

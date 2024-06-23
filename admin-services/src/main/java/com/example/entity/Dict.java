package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;

@Data
@TableName("dict")
public class Dict extends BaseEntity {
    /** 字典项名称 */
    private String dictName ;
    /** key值 */
    private String code ;
}

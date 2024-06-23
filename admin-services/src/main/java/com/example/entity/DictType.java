package com.example.entity;

import com.example.baseEntity.BaseEntity;
import lombok.Data;

@Data
public class DictType extends BaseEntity {
    /** 字典表code */
    private String dictCode ;
    /** 字典项名称 */
    private String name ;
    /** 值 */
    private String value ;
}

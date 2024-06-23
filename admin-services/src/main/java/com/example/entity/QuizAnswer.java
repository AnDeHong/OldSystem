package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.baseEntity.BaseEntity;
import lombok.Data;

@Data
@TableName("interaction_answer")
public class QuizAnswer extends BaseEntity {
    /** 问题id */
    private Integer interactionId;
    /** 回答内容 */
    private String answerContent;

}

package com.example.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QuizQueryVo {
    /** 提问者 */
    private String quizName;
    /** 筛选时间 */
    private String quizDate ;
    /** 是否解答 */
    private String status ;
    /** 提问时间 */
    private String createTime ;
}

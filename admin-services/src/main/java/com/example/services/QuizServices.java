package com.example.services;

import com.example.entity.Quiz;
import com.example.entity.QuizAnswer;
import com.example.mapper.QuizAnswerMapper;
import com.example.mapper.QuizMapper;
import com.example.utils.Const;
import com.example.utils.DateUtils;
import com.example.vo.QuizQueryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class QuizServices {

    @Resource
    private QuizMapper quizMapper;
    @Resource
    private QuizAnswerMapper quizAnswerMapper;

    public List<Quiz> getALl(QuizQueryVo vo) {
        if (vo.getQuizDate().equals("Invalid date")) {
            vo.setQuizDate("");
        }
        if (vo.getCreateTime().equals("Invalid date")) {
            vo.setCreateTime("");
        }
        return this.quizMapper.getAll(vo);
    }

    public List<QuizAnswer> getQuizAnswer(int id) {
        return this.quizMapper.getQuizAnswer(id);
    }

    public Boolean delete(int id) {
        Quiz quiz = this.quizMapper.getById(id);
        if (quiz != null) {
            quiz.setDeleteMark(Const.YES);
            this.quizMapper.updateById(quiz);
            List<QuizAnswer> quizAnswers = this.quizMapper.getQuizAnswer(id);
            if (quizAnswers.size() > 0) {
                for (QuizAnswer quizAnswer: quizAnswers) {
                    quizAnswer.setDeleteMark(Const.YES);
                    this.quizAnswerMapper.updateById(quizAnswer);
                }
            }
            return true;
        }
        return false;
    }
}

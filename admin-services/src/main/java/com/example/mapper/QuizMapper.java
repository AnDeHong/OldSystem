package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Quiz;
import com.example.entity.QuizAnswer;
import com.example.vo.QuizQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuizMapper extends BaseMapper<Quiz> {

    List<Quiz> getAll(QuizQueryVo vo);

    Quiz getById(@Param("id") int id);

    List<QuizAnswer> getQuizAnswer(@Param("id") int id);
}

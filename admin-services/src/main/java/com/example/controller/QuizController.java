package com.example.controller;

import com.example.entity.Quiz;
import com.example.entity.QuizAnswer;
import com.example.response.Response;
import com.example.services.QuizServices;
import com.example.vo.ActivityVo;
import com.example.vo.QuizQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin-quiz")
@Api(tags = "问答模块", description = "问答模块")
public class QuizController extends BaseController {
    @Resource
    private QuizServices quizServices;

    @PostMapping("/get-quiz")
    @ApiOperation("获取问题")
    public Response<List<Quiz>> getAll(@RequestBody QuizQueryVo vo) throws ParseException {
        return this.ok(this.quizServices.getALl(vo));
    }
    @GetMapping("/get-quiz-answer")
    @ApiOperation("获取问题答案")
    public Response<List<QuizAnswer>> getQuizAnswer(@RequestParam("id") int id) {
        return this.ok(this.quizServices.getQuizAnswer(id));
    }
    @DeleteMapping("/quiz-delete/{id}")
    @ApiOperation("删除问题")
    public Response<Boolean> delete(@PathVariable("id") int id){
        return this.ok(this.quizServices.delete(id));
    }
}

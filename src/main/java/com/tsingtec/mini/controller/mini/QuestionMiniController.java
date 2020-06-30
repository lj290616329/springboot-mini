package com.tsingtec.mini.controller.mini;

import com.tsingtec.mini.aop.annotation.PassToken;
import com.tsingtec.mini.service.QuestionService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.vo.resp.app.question.QuestionListRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/21 13:50
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wxs/question")
@Api(tags = "小程序模块--调查表")
public class QuestionMiniController {

    @Autowired
    private QuestionService questionService;

    @PassToken
    @GetMapping("/list")
    @ApiOperation(value = "获取调查问卷列表")
    public DataResult<List<QuestionListRespVO>> list() {
        DataResult<List<QuestionListRespVO>> result = DataResult.success();
        result.setData(questionService.getQuestionList());
        return result;
    }
}

package com.tsingtec.mini.controller.manager.question;

import com.tsingtec.mini.aop.annotation.LogAnnotation;
import com.tsingtec.mini.entity.mini.Question;
import com.tsingtec.mini.service.QuestionService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.vo.req.question.QuestionAddReqVO;
import com.tsingtec.mini.vo.req.question.QuestionUpdateReqVO;
import com.tsingtec.mini.vo.req.sort.SortReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/19 17:29
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "应用模块-问题管理")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question")
    @ApiOperation(value = "获取问题列表接口")
    @LogAnnotation(title = "问题管理", action = "获取问题列表接口")
    public DataResult<List<Question>> getAll() {
        DataResult<List<Question>> result = DataResult.success();
        result.setData(questionService.getAll());
        return result;
    }

    @GetMapping("/question/radios")
    @ApiOperation(value = "获取单选问题列表接口")
    @LogAnnotation(title = "问题管理", action = "获取单选问题列表接口")
    public DataResult<List<Question>> pageInfo() {
        DataResult<List<Question>> result = DataResult.success();
        result.setData(questionService.getAllRadios());
        return result;
    }

    @PostMapping("/question")
    @ApiOperation(value = "新增问题接口")
    @LogAnnotation(title = "问题管理",action = "新增问题")
    public DataResult addUser(@RequestBody QuestionAddReqVO vo){
        questionService.save(vo);
        return DataResult.success();
    }

    @PutMapping("/question")
    @ApiOperation(value = "修改问题接口")
    @LogAnnotation(title = "问题管理",action = "修改问题")
    public DataResult update(@RequestBody QuestionUpdateReqVO vo){
        questionService.update(vo);
        return DataResult.success();
    }

    @PutMapping("/question/sort")
    @ApiOperation(value = "修改问题接口")
    @LogAnnotation(title = "问题管理",action = "修改问题")
    public DataResult sort(@RequestBody List<SortReqVO> vo){
        questionService.sort(vo);
        return DataResult.success();
    }

    @DeleteMapping("/question")
    @ApiOperation(value = "删除问题接口")
    @LogAnnotation(title = "问题管理",action = "删除问题")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "问题id集合") List<Integer> aids){
        questionService.deleteBatch(aids);
        return DataResult.success();
    }
    
    
}

package com.tsingtec.mini.controller.manager.question;

import com.tsingtec.mini.aop.annotation.LogAnnotation;
import com.tsingtec.mini.entity.mini.QuestionGroup;
import com.tsingtec.mini.service.QuestionGroupService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.vo.req.question.QuestionGroupReqVO;
import com.tsingtec.mini.vo.req.sort.SortReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/19 17:29
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "应用模块-问题类型管理")
public class QuestionGroupController {

    @Autowired
    private QuestionGroupService questionGroupService;

    @GetMapping("/questionGroup")
    @ApiOperation(value = "分页获取问题类型列表接口")
    @LogAnnotation(title = "问题类型管理", action = "分页获取问题类型列表")
    public DataResult<List<QuestionGroup>> pageInfo() {
        DataResult<List<QuestionGroup>> result = DataResult.success();
        result.setData(questionGroupService.getAll());
        return result;
    }

    @PostMapping("/questionGroup")
    @ApiOperation(value = "新增问题类型接口")
    @LogAnnotation(title = "问题类型管理",action = "新增问题类型")
    public DataResult addUser(@RequestBody @Valid String name){
        questionGroupService.save(name);
        return DataResult.success();
    }

    @PutMapping("/questionGroup")
    @ApiOperation(value = "修改问题类型接口")
    @LogAnnotation(title = "问题类型管理",action = "修改问题类型")
    public DataResult update(@RequestBody QuestionGroupReqVO vo){
        questionGroupService.update(vo);
        return DataResult.success();
    }

    @PutMapping("/questionGroup/sort")
    @ApiOperation(value = "修改问题类型接口")
    @LogAnnotation(title = "问题类型管理",action = "修改问题类型")
    public DataResult sort(@RequestBody List<SortReqVO> vo){
        questionGroupService.sort(vo);
        return DataResult.success();
    }

    @DeleteMapping("/questionGroup")
    @ApiOperation(value = "删除问题类型接口")
    @LogAnnotation(title = "问题类型管理",action = "删除问题类型")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "问题类型id集合") List<Integer> aids){
        questionGroupService.deleteBatch(aids);
        return DataResult.success();
    }
    
    
}

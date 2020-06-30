package com.tsingtec.mini.controller.manager.news;

import com.tsingtec.mini.aop.annotation.LogAnnotation;
import com.tsingtec.mini.entity.news.ArticleTag;
import com.tsingtec.mini.service.ArticleTagService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.vo.req.news.ArticleTagUpdateReqVO;
import com.tsingtec.mini.vo.req.other.SortReqVO;
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
public class ArticleTagController {

    @Autowired
    private ArticleTagService articleTagService;

    @GetMapping("/articleTag")
    @ApiOperation(value = "分页获取问题类型列表接口")
    @LogAnnotation(title = "问题类型管理", action = "分页获取问题类型列表")
    public DataResult<List<ArticleTag>> pageInfo() {
        DataResult<List<ArticleTag>> result = DataResult.success();
        result.setData(articleTagService.getAll());
        return result;
    }

    @PostMapping("/articleTag")
    @ApiOperation(value = "新增问题类型接口")
    @LogAnnotation(title = "问题类型管理",action = "新增问题类型")
    public DataResult addUser(@RequestBody @Valid String name){
        articleTagService.save(name);
        return DataResult.success();
    }

    @PutMapping("/articleTag")
    @ApiOperation(value = "修改问题类型接口")
    @LogAnnotation(title = "问题类型管理",action = "修改问题类型")
    public DataResult update(@RequestBody ArticleTagUpdateReqVO vo){
        articleTagService.update(vo);
        return DataResult.success();
    }

    @PutMapping("/articleTag/sort")
    @ApiOperation(value = "修改问题类型接口")
    @LogAnnotation(title = "问题类型管理",action = "修改问题类型")
    public DataResult sort(@RequestBody List<SortReqVO> vo){
        articleTagService.sort(vo);
        return DataResult.success();
    }

    @DeleteMapping("/articleTag")
    @ApiOperation(value = "删除问题类型接口")
    @LogAnnotation(title = "问题类型管理",action = "删除问题类型")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "问题类型id集合") List<Integer> aids){
        articleTagService.deleteBatch(aids);
        return DataResult.success();
    }
    
    
}

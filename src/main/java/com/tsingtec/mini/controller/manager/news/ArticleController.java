package com.tsingtec.mini.controller.manager.news;

import com.tsingtec.mini.aop.annotation.LogAnnotation;
import com.tsingtec.mini.entity.news.Article;
import com.tsingtec.mini.service.ArticleService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import com.tsingtec.mini.vo.req.news.ArticleAddReqVO;
import com.tsingtec.mini.vo.req.news.ArticlePageReqVO;
import com.tsingtec.mini.vo.req.other.SortReqVO;
import com.tsingtec.mini.vo.req.news.ArticleUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "组织模块-图文管理")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    @RequiresPermissions("news:article:list")
    @ApiOperation(value = "分页获取图文列表接口")
    @LogAnnotation(title = "图文管理", action = "分页获取图文列表")
    public DataResult<List<Article>> pageInfo(ArticlePageReqVO vo) {
        DataResult<List<Article>> result = DataResult.success();
        vo.setAid(HttpContextUtils.getAid());
        result.setData(articleService.pageInfo(vo));
        return result;
    }

    @PostMapping("/article")
    @ApiOperation(value = "新增图文接口")
    @RequiresPermissions("news:article:add")
    @LogAnnotation(title = "图文管理",action = "新增图文")
    public DataResult addUser(@RequestBody @Valid ArticleAddReqVO vo){
        vo.setAid(HttpContextUtils.getAid());
        articleService.save(vo);
        return DataResult.success();
    }

    @PutMapping("/article")
    @ApiOperation(value = "修改图文接口")
    @RequiresPermissions("news:article:update")
    @LogAnnotation(title = "图文管理",action = "修改图文")
    public DataResult update(@RequestBody ArticleUpdateReqVO vo){
        articleService.update(vo);
        return DataResult.success();
    }

    @PutMapping("/article/sort")
    @ApiOperation(value = "修改图文接口")
    @RequiresPermissions("news:article:list")
    @LogAnnotation(title = "图文管理",action = "修改图文")
    public DataResult sort(@RequestBody List<SortReqVO> vo){
        articleService.sort(vo);
        return DataResult.success();
    }

    @DeleteMapping("/article")
    @ApiOperation(value = "删除图文接口")
    @RequiresPermissions("news:article:delete")
    @LogAnnotation(title = "图文管理",action = "删除图文")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "图文id集合") List<Integer> aids){
        articleService.deleteBatch(aids);
        return DataResult.success();
    }
}

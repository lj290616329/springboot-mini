package com.tsingtec.mini.controller.mini;

import com.tsingtec.mini.aop.annotation.PassToken;
import com.tsingtec.mini.entity.news.Article;
import com.tsingtec.mini.entity.news.ArticleTag;
import com.tsingtec.mini.service.ArticleService;
import com.tsingtec.mini.service.ArticleTagService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.IPUtils;
import com.tsingtec.mini.vo.req.news.ArticlePageReqVO;
import com.tsingtec.mini.vo.resp.news.ArticleRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/28 17:40
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wxs/news")
@Api(tags = "小程序模块--图文管理")
public class ArticleMiniController {

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private ArticleService articleService;

    @PassToken
    @GetMapping("tags")
    @ApiOperation(value = "获取图文标签列表")
    public DataResult<List<ArticleTag>> tags(){
        DataResult<List<ArticleTag>> result = DataResult.success();
        List<ArticleTag> articleTags = articleTagService.findByStatus();
        result.setData(articleTags);
        return result;
    }

    @PassToken
    @PostMapping("articles")
    @ApiOperation(value = "获取图文标签列表")
    public DataResult<Page<Article>> articles(@RequestBody ArticlePageReqVO vo){
        System.out.println(vo.toString());
        DataResult<Page<Article>> result = DataResult.success();
        result.setData(articleService.getPage(vo));
        return result;
    }

    @PassToken
    @GetMapping("article/{id}")
    @ApiOperation(value = "获取图文标签列表")
    public DataResult<ArticleRespVO> article(HttpServletRequest request, @PathVariable("id")Integer id){
        DataResult<ArticleRespVO> result = DataResult.success();
        result.setData(articleService.findById(id));
        //因为是不知道用户信息的所以直接使用ip作为键值进行缓存key
        String ip = IPUtils.getIpAddr(request);
        articleService.hits(ip,id);
        return result;
    }
}

package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.news.Article;
import com.tsingtec.mini.vo.req.news.ArticleAddReqVO;
import com.tsingtec.mini.vo.req.news.ArticlePageReqVO;
import com.tsingtec.mini.vo.req.news.ArticleUpdateReqVO;
import com.tsingtec.mini.vo.req.other.SortReqVO;
import com.tsingtec.mini.vo.resp.news.ArticleRespVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArticleService {

    ArticleRespVO findById(Integer id);

    List<Article> pageInfo(ArticlePageReqVO vo);

    void save(ArticleAddReqVO vo);

    void update(ArticleUpdateReqVO vo);

    void deleteBatch(List<Integer> ids);

    void sort(List<SortReqVO> sorts);

    Page<Article> getPage(ArticlePageReqVO vo);

    void hits(String ip, Integer id);

}

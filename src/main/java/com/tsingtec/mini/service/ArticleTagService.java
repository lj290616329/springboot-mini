package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.news.ArticleTag;
import com.tsingtec.mini.vo.req.news.ArticleTagUpdateReqVO;
import com.tsingtec.mini.vo.req.other.SortReqVO;

import java.util.List;

public interface ArticleTagService {
    
    void save(String name);

    List<ArticleTag> getAll();

    void update(ArticleTagUpdateReqVO vo);

    void deleteBatch(List<Integer> ids);

    void sort(List<SortReqVO> sorts);

    List<ArticleTag> findByStatus();
}

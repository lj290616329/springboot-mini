package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.config.cache.EhCacheConfig;
import com.tsingtec.mini.entity.news.ArticleTag;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.repository.ArticleTagRepository;
import com.tsingtec.mini.service.ArticleTagService;
import com.tsingtec.mini.vo.req.news.ArticleTagUpdateReqVO;
import com.tsingtec.mini.vo.req.other.SortReqVO;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Autowired
    private ArticleTagRepository articleTagRepository;

    private Cache<String, AtomicInteger> maxidCache;

    public ArticleTagServiceImpl(EhCacheConfig ehCacheConfig){
        this.maxidCache = ehCacheConfig.ehCacheManager().getCache("data");
    }

    private Integer maxId(){
        Integer maxid = articleTagRepository.maxId();
        if(null==maxid){
            return 0;
        }
        return maxid;
    }

    private Integer getMaxid(){
        AtomicInteger maxid =  maxidCache.get("articleTagMaxId");
        if(maxid==null){
            maxid = new AtomicInteger(maxId());
            maxidCache.put("articleTagMaxId", maxid);
        }
        return maxid.incrementAndGet();
    }

    @Override
    @Transactional
    public void save(String name) {
        ArticleTag questionType = articleTagRepository.findByName(name);
        if(null!=questionType){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"该类型已存在");
        }
        questionType = new ArticleTag();
        questionType.setName(name);
        questionType.setSort(getMaxid());
        articleTagRepository.save(questionType);
    }

    @Override
    public List<ArticleTag> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC,"sort","id");
        return articleTagRepository.findAll(sort);
    }

    @Override
    @Transactional
    public void update(ArticleTagUpdateReqVO vo) {
        ArticleTag savequestionType = articleTagRepository.getOne(vo.getId());
        if(null==savequestionType){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"不存在的记录");
        }

        ArticleTag questionType = articleTagRepository.findByName(vo.getName());
        if(null!=questionType &&!questionType.getId().equals(vo.getId())){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"该类型已存在");
        }
        savequestionType.setName(vo.getName());
        articleTagRepository.save(savequestionType);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Integer> ids) {
        articleTagRepository.deleteBatch(ids);
    }

    @Override
    @Transactional
    public void sort(List<SortReqVO> sorts) {
        sorts.forEach(sort -> {
            articleTagRepository.sort(sort.getId(),sort.getToSort());
        });
    }

    @Override
    public List<ArticleTag> findByStatus() {
        List<ArticleTag> articleTags = getAll();
        return articleTags.stream().filter(a->a.getStatus()).collect(Collectors.toList());
    }
}

package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.config.cache.EhCacheConfig;
import com.tsingtec.mini.entity.news.Article;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.repository.ArticleRepository;
import com.tsingtec.mini.service.ArticleService;
import com.tsingtec.mini.utils.BeanMapper;
import com.tsingtec.mini.utils.BeanUtil;
import com.tsingtec.mini.vo.req.news.ArticleAddReqVO;
import com.tsingtec.mini.vo.req.news.ArticlePageReqVO;
import com.tsingtec.mini.vo.req.news.ArticleUpdateReqVO;
import com.tsingtec.mini.vo.req.other.SortReqVO;
import com.tsingtec.mini.vo.resp.news.ArticleRespVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @Author lj
 * @Date 2020/6/12 14:17
 * @Version 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {


    private Cache<String, AtomicInteger> maxidCache;

    public ArticleServiceImpl(EhCacheConfig ehCacheConfig){
        this.maxidCache = ehCacheConfig.ehCacheManager().getCache("data");
    }

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public ArticleRespVO findById(Integer id) {
        Article article = articleRepository.getOne(id);
        if(null==article){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"图文不存在!");
        }
        return BeanMapper.map(article,ArticleRespVO.class);
    }

    @Override
    public List<Article> pageInfo(ArticlePageReqVO vo) {
        Sort sort = Sort.by(Sort.Direction.DESC,"sort","id");
        return articleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();

            if (!StringUtils.isEmpty(vo.getTitle())){
                predicates.add(criteriaBuilder.like(root.get("title"),"%"+vo.getTitle()+"%"));
            }

            if (null != vo.getTag()){
                predicates.add(criteriaBuilder.greaterThan(root.get("tag"),vo.getTag()));
            }

            if (null != vo.getStartTime()){
                predicates.add(criteriaBuilder.greaterThan(root.get("createTime"),vo.getStartTime()));
            }

            if (null !=vo.getEndTime()){
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"),vo.getEndTime()));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },sort);
    }

    private Integer maxId(){
        Integer maxid = articleRepository.maxId();
        if(null==maxid){
            return 0;
        }
        return maxid;
    }

    private Integer getMaxid(){
        AtomicInteger maxid =  maxidCache.get("articleMaxId");
        if(maxid==null){
            maxid = new AtomicInteger(maxId());
            maxidCache.put("articleMaxId", maxid);
        }
        return maxid.incrementAndGet();
    }

    @Override
    public void save(ArticleAddReqVO vo) {
        Article article = new Article();
        article = BeanMapper.map(vo,Article.class);
        article.setSort(getMaxid());
        articleRepository.save(article);
    }

    @Override
    public void update(ArticleUpdateReqVO vo) {
        Article article = articleRepository.getOne(vo.getId());
        if(null==article){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"您修改的图文不存在!");
        }
        BeanUtil.copyPropertiesIgnoreNull(vo,article);
        articleRepository.save(article);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        articleRepository.deleteBatch(ids);
    }

    @Override
    public void sort(List<SortReqVO> sorts) {
        sorts.forEach(sort -> {
            articleRepository.sort(sort.getId(),sort.getToSort());
        });
    }

    @Override
    public Page<Article> getPage(ArticlePageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return articleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();

            if (null != vo.getAid()){
                predicates.add(criteriaBuilder.equal(root.get("aid"),vo.getAid()));
            }

            if (!StringUtils.isBlank(vo.getTitle())){
                predicates.add(criteriaBuilder.like(root.get("title"),"%"+vo.getTitle()+"%"));
            }

            if (null != vo.getTag()){
                predicates.add(criteriaBuilder.equal(root.get("tag"),vo.getTag()));
            }
            if (null !=vo.getStartTime()){
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"),vo.getStartTime()));
            }
            if (null !=vo.getEndTime()){
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"),vo.getEndTime()));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },pageable);
    }

    @Override
    @Cacheable(value = "data",key = "#p0+'#'+#p1")
    public void hits(String ip, Integer id) {
        articleRepository.hits(id);
    }
}

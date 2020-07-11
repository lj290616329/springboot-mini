package com.tsingtec.mini.service.impl;

import com.google.common.collect.Lists;
import com.tsingtec.mini.config.cache.EhCacheConfig;
import com.tsingtec.mini.entity.mini.Question;
import com.tsingtec.mini.entity.mini.QuestionGroup;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.repository.QuestionRepository;
import com.tsingtec.mini.service.QuestionGroupService;
import com.tsingtec.mini.service.QuestionService;
import com.tsingtec.mini.utils.BeanMapper;
import com.tsingtec.mini.utils.BeanUtil;
import com.tsingtec.mini.vo.req.other.SortReqVO;
import com.tsingtec.mini.vo.req.other.SwitchReqVO;
import com.tsingtec.mini.vo.req.question.QuestionAddReqVO;
import com.tsingtec.mini.vo.resp.app.question.QuestionListRespVO;
import com.tsingtec.mini.vo.req.question.QuestionUpdateReqVO;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionGroupService questionGroupService;

    private Cache<String, AtomicInteger> maxidCache;

    public QuestionServiceImpl(EhCacheConfig ehCacheConfig){
        this.maxidCache = ehCacheConfig.ehCacheManager().getCache("data");
    }

    private Integer maxId(){
        Integer maxid = questionRepository.maxId();
        if(null==maxid){
            return 0;
        }
        return maxid;
    }

    private Integer getMaxid(){
        AtomicInteger maxid =  maxidCache.get("questionMaxId");
        if(maxid==null){
            maxid = new AtomicInteger(maxId());
            maxidCache.put("questionMaxId", maxid);
        }
        return maxid.incrementAndGet();
    }

    @Override
    @Transactional
    public void save(QuestionAddReqVO vo) {
        Question question = new Question();
        question = BeanMapper.map(vo,Question.class);
        question.setSort(getMaxid());
        questionRepository.save(question);
    }

    @Override
    public List<Question> getAll() {
        List<Question> questions = questionRepository.findAll();
        Collections.sort(questions, Comparator.comparing(Question::getGroupSort).thenComparing(Question::getSort));
        return questions;
    }

    @Override
    public List<Question> getAllRadios() {
        return questionRepository.findByType("radio");
    }

    @Override
    @Transactional
    public void update(QuestionUpdateReqVO vo) {
        Question savequestion = questionRepository.getOne(vo.getId());
        if(null==savequestion){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"不存在的记录");
        }
        BeanUtil.copyPropertiesIgnoreNull(vo,savequestion);
        questionRepository.save(savequestion);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Integer> ids) {
        questionRepository.deleteBatch(ids);
    }

    @Override
    @Transactional
    public void sort(List<SortReqVO> sorts) {
        sorts.forEach(sort -> {
            questionRepository.sort(sort.getId(),sort.getToSort());
        });
    }

    @Override
    public List<QuestionListRespVO> getQuestionList() {
        List<QuestionGroup> questionGroups = questionGroupService.getAll();
        List<QuestionListRespVO> questionListRespVOS = Lists.newArrayList();
        questionGroups.forEach(questionGroup -> {
            questionListRespVOS.add(new QuestionListRespVO(questionGroup.getName(),questionRepository.findByGroupNameAndBasicOrderBySortAsc(questionGroup.getName(),true)));
        });
        return questionListRespVOS;
    }

    @Override
    @Transactional
    public void basic(SwitchReqVO vo) {
        Question question = questionRepository.getOne(vo.getId());
        if(null == question){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"不存在的记录");
        }
        question.setBasic(vo.getOpen());
        questionRepository.save(question);

    }
}

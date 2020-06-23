package com.tsingtec.mini.service.impl;

import com.google.common.collect.Lists;
import com.tsingtec.mini.config.cache.EhCacheConfig;
import com.tsingtec.mini.entity.mini.Question;
import com.tsingtec.mini.entity.mini.QuestionGroup;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.repository.QuestionGroupRepository;
import com.tsingtec.mini.repository.QuestionRepository;
import com.tsingtec.mini.service.QuestionGroupService;
import com.tsingtec.mini.vo.req.question.QuestionGroupReqVO;
import com.tsingtec.mini.vo.req.sort.SortReqVO;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class QuestionGroupServiceImpl implements QuestionGroupService {

    @Autowired
    private QuestionGroupRepository questionGroupRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Cache<String, AtomicInteger> maxidCache;

    public QuestionGroupServiceImpl(EhCacheConfig ehCacheConfig){
        this.maxidCache = ehCacheConfig.ehCacheManager().getCache("data");
    }

    private Integer maxId(){
        Integer maxid = questionGroupRepository.maxId();
        if(null==maxid){
            return 0;
        }
        return maxid;
    }

    private Integer getMaxid(){
        AtomicInteger maxid =  maxidCache.get("questionTypeMaxId");
        if(maxid==null){
            maxid = new AtomicInteger(maxId());
            maxidCache.put("questionTypeMaxId", maxid);
        }
        return maxid.incrementAndGet();
    }

    @Override
    public void save(String name) {
        QuestionGroup questionType = questionGroupRepository.findByName(name);
        if(null!=questionType){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"该类型已存在");
        }
        questionType = new QuestionGroup();
        questionType.setName(name);
        questionType.setSort(getMaxid());
        questionGroupRepository.save(questionType);
    }

    @Override
    public List<QuestionGroup> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC,"sort","id");
        return questionGroupRepository.findAll(sort);
    }

    @Override
    public void update(QuestionGroupReqVO vo) {
        QuestionGroup savequestionType = questionGroupRepository.getOne(vo.getId());
        if(null==savequestionType){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"不存在的记录");
        }

        QuestionGroup questionType = questionGroupRepository.findByName(vo.getName());
        if(null!=questionType &&!questionType.getId().equals(vo.getId())){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"该类型已存在");
        }
        savequestionType.setName(vo.getName());
        questionGroupRepository.save(savequestionType);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        questionGroupRepository.deleteBatch(ids);
    }

    @Override
    public void sort(List<SortReqVO> sorts) {
        List<Question> questions = Lists.newArrayList();
        List<QuestionGroup> questionGroups = Lists.newArrayList();
        sorts.forEach(sort -> {
            QuestionGroup questionGroup = questionGroupRepository.getOne(sort.getId());
            questionGroup.setSort(sort.getToSort());
            questionGroups.add(questionGroup);

            List<Question> sortQuestions = questionRepository.findByGroupSort(sort.getOldSort());
            sortQuestions.forEach(question -> {
                question.setGroupSort(sort.getToSort());
            });
            questions.addAll(sortQuestions);
            //questionGroupRepository.sort(sort.getId(),sort.getToSort());
        });
        questionRepository.saveAll(questions);
        questionGroupRepository.saveAll(questionGroups);
    }
}

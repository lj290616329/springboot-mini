package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mini.Question;
import com.tsingtec.mini.vo.req.question.QuestionAddReqVO;
import com.tsingtec.mini.vo.req.question.QuestionListRespVO;
import com.tsingtec.mini.vo.req.question.QuestionUpdateReqVO;
import com.tsingtec.mini.vo.req.sort.SortReqVO;

import java.util.List;

public interface QuestionService {
    void save(QuestionAddReqVO vo);

    List<Question> getAll();

    List<Question> getAllRadios();

    void update(QuestionUpdateReqVO vo);

    void deleteBatch(List<Integer> ids);

    void sort(List<SortReqVO> sorts);

    List<QuestionListRespVO> getQuestionList();
}

package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mini.Question;
import com.tsingtec.mini.vo.req.other.SortReqVO;
import com.tsingtec.mini.vo.req.other.SwitchReqVO;
import com.tsingtec.mini.vo.req.question.QuestionAddReqVO;
import com.tsingtec.mini.vo.resp.app.question.QuestionListRespVO;
import com.tsingtec.mini.vo.req.question.QuestionUpdateReqVO;

import java.util.List;

public interface QuestionService {
    void save(QuestionAddReqVO vo);

    List<Question> getAll();

    List<Question> getAllRadios();

    void update(QuestionUpdateReqVO vo);

    void deleteBatch(List<Integer> ids);

    void sort(List<SortReqVO> sorts);

    List<QuestionListRespVO> getQuestionList();

    void basic(SwitchReqVO vo);
}

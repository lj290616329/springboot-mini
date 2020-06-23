package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mini.QuestionGroup;
import com.tsingtec.mini.vo.req.question.QuestionGroupReqVO;
import com.tsingtec.mini.vo.req.sort.SortReqVO;

import java.util.List;

public interface QuestionGroupService {
    void save(String name);

    List<QuestionGroup> getAll();

    void update(QuestionGroupReqVO vo);

    void deleteBatch(List<Integer> ids);

    void sort(List<SortReqVO> sorts);
}

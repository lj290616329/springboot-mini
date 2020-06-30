package com.tsingtec.mini.vo.resp.app.question;

import lombok.Data;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/21 12:59
 * @Version 1.0
 */
@Data
public class QuestionResultListRespVO {
    private String name;
    private List<QuestionRespVO> questionList;

    public QuestionResultListRespVO(String name, List<QuestionRespVO> questionList) {
        this.name = name;
        this.questionList = questionList;
    }
}

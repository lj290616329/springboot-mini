package com.tsingtec.mini.vo.resp.app.question;

import com.tsingtec.mini.entity.mini.Question;
import lombok.Data;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/21 12:59
 * @Version 1.0
 */
@Data
public class QuestionListRespVO {
    private String name;
    private List<Question> questionList;

    public QuestionListRespVO(String name, List<Question> questionList) {
        this.name = name;
        this.questionList = questionList;
    }
}

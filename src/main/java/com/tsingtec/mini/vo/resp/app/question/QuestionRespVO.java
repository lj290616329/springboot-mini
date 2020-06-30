package com.tsingtec.mini.vo.resp.app.question;

import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/19 17:10
 * @Version 1.0
 */
@Data
public class QuestionRespVO {
    private Integer id;
    private String value;
    private Integer groupSort;
    private String groupName;
    private String title;
    private Integer sort;
}

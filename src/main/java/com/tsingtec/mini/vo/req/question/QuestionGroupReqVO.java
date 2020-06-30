package com.tsingtec.mini.vo.req.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2020/6/19 17:14
 * @Version 1.0
 */
@Data
public class QuestionGroupReqVO {

    @ApiModelProperty(value = "id",name="id")
    @NotNull(message = "id")
    private Integer id;

    @ApiModelProperty(value = "父级名称",name="name")
    @NotBlank(message = "父级名称不能为空")
    private String name;
}

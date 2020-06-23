package com.tsingtec.mini.vo.req.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2020/6/20 8:20
 * @Version 1.0
 */
@Data
public class QuestionAddReqVO {

    @ApiModelProperty(value = "显示名称",name="name")
    @NotNull(message = "显示名称不能为空")
    private Integer groupSort;//父级id

    @ApiModelProperty(value = "显示名称",name="name")
    @NotBlank(message = "显示名称不能为空")
    private String groupName;//父级名称

    @ApiModelProperty(value = "显示名称",name="name")
    @NotBlank(message = "显示名称不能为空")
    private String title;//题目 (性别)

    @ApiModelProperty(value = "显示名称",name="name")
    @NotBlank(message = "显示名称不能为空")
    private String showTitle;//显示题目(您的性别?)

    @ApiModelProperty(value = "显示名称",name="name")
    @NotBlank(message = "显示名称不能为空")
    private String type;//类型 暂时支持 radio checkbox input picker textarea 这5类

    private String inputType="text";//如果是输入框 默认类型

    private String pickerMode="selector";//selector,time,date,region

    private Boolean required=false;//是否必填

    private String options;//选项 逗号分隔

    private Boolean ifNeed=false;//是否关联

    private String need;//关联的内容

}

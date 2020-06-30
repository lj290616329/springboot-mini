package com.tsingtec.mini.vo.req.mini.information;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author lj
 * @Date 2020/6/25 13:36
 * @Version 1.0
 */
@Data
public class InformationReqVO {

    @ApiModelProperty(value = "电话",name="phone")
    @NotBlank(message = "电话不能为空")
    private String phone;//电话

    @ApiModelProperty(value = "姓名",name="name")
    @NotBlank(message = "姓名不能为空")
    private String name;//姓名

    @ApiModelProperty(value = "性别",name="sex")
    @NotNull(message = "性别不能为空")
    private Integer sex;//1男2女0未知

    @ApiModelProperty(value = "生日",name="birthday")
    @NotNull(message = "生日不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;//生日

    @ApiModelProperty(value = "婚姻",name="marry")
    @NotNull(message = "婚姻不能为空")
    private Integer marry;//1已婚2未婚3离异

    @ApiModelProperty(value = "职业",name="profession")
    @NotBlank(message = "职业不能为空")
    private String profession;//职业

    @ApiModelProperty(value = "籍贯",name="domicile")
    @NotBlank(message = "籍贯不能为空")
    private String domicile;//籍贯

    @ApiModelProperty(value = "居住地址",name="address")
    @NotBlank(message = "居住地址不能为空")
    private String address;//居住地址
}

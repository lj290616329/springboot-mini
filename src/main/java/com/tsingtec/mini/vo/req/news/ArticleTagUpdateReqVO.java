package com.tsingtec.mini.vo.req.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2020/6/24 15:03
 * @Version 1.0
 */
@Data
public class ArticleTagUpdateReqVO {
    @ApiModelProperty(value = "id",name="id")
    @NotNull(message = "id")
    private Integer id;

    @ApiModelProperty(value = "父级名称",name="pName")
    @NotBlank(message = "父级名称不能为空")
    private String name;
}

package com.tsingtec.mini.vo.req.websocket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2020/6/7 16:38
 * @Version 1.0
 */
@Data
public class SignReqVO {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Integer id;

    private String sign;
}

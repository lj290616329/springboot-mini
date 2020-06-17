package com.tsingtec.mini.vo.req.free;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author lj
 * @Date 2020/6/11 14:50
 * @Version 1.0
 */
@Data
public class FreeLoginReqVO {

    @ApiModelProperty(value = "token")
    @NotBlank(message = "token名称不能为空")
    private String token;//token

    @ApiModelProperty(value = "是否同意")
    private Boolean aggre=false;//是否同意
}

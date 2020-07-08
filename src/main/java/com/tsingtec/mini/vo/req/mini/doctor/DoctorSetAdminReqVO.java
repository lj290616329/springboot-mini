package com.tsingtec.mini.vo.req.mini.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2020/7/1 11:45
 * @Version 1.0
 */
@Data
public class DoctorSetAdminReqVO {

    @ApiModelProperty(value = "医生id",name="id")
    @NotNull(message = "医生id不能为空")
    private Integer id;

    @ApiModelProperty(value = "账号id",name="aid")
    @NotNull(message = "账号id不能为空")
    private Integer aid;

}

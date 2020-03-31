package com.tsingtec.mini.vo.resp.sys.admin;

import com.tsingtec.mini.entity.sys.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author lj
 * @Date 2020/3/8 15:10
 * @Version 1.0
 */
@Data
public class AdminRoleRespVO {

    @ApiModelProperty("所有角色集合")
    private List<Role> allRole;

    @ApiModelProperty(value = "用户所拥有角色集合")
    private List<Integer> ownRoles;
}

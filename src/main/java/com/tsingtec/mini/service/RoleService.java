package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.sys.Role;
import com.tsingtec.mini.vo.req.sys.role.RoleAddReqVO;
import com.tsingtec.mini.vo.req.sys.role.RolePageReqVO;
import com.tsingtec.mini.vo.req.sys.role.RoleUpdateReqVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    Role addRole(RoleAddReqVO vo);

    void updateRole(RoleUpdateReqVO vo);

    Role findById(Integer id);

    Page<Role> pageInfo(RolePageReqVO vo);

    void deleteBatch(List<Integer> rids);
}

package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.sys.Admin;
import com.tsingtec.mini.vo.req.sys.admin.AdminAddReqVO;
import com.tsingtec.mini.vo.req.sys.admin.AdminPageReqVO;
import com.tsingtec.mini.vo.req.sys.admin.AdminPwdReqVO;
import com.tsingtec.mini.vo.req.sys.admin.AdminUpdateReqVO;
import com.tsingtec.mini.vo.resp.sys.admin.AdminRoleRespVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {

    Admin findById(Integer id);

    Page<Admin> pageInfo(AdminPageReqVO vo);

    void save(Admin admin);

    void insert(AdminAddReqVO vo);

    Admin findByLoginName(String loginName);

    void update(AdminUpdateReqVO vo);

    void updatePwd(Integer id, AdminPwdReqVO vo);

    void deleteBatch(List<Integer> aids);

    AdminRoleRespVO getAdminRole(Integer aid);

    void setAdminRole(Integer aid, List<Integer> roleIds);
}

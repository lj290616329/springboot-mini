package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.file.Material;
import com.tsingtec.mini.entity.sys.Admin;
import com.tsingtec.mini.vo.req.material.MaterialPageReqVO;
import com.tsingtec.mini.vo.req.sys.admin.AdminAddReqVO;
import com.tsingtec.mini.vo.req.sys.admin.AdminPageReqVO;
import com.tsingtec.mini.vo.req.sys.admin.AdminPwdReqVO;
import com.tsingtec.mini.vo.req.sys.admin.AdminUpdateReqVO;
import com.tsingtec.mini.vo.resp.sys.admin.AdminRoleRespVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MaterialService {

    Page<Material> pageInfo(MaterialPageReqVO vo);

    void save(Material material);

    void deleteBatch(List<Integer> mids);

}

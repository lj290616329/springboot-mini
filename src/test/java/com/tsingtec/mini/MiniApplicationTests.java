package com.tsingtec.mini;

import com.tsingtec.mini.entity.sys.Menu;
import com.tsingtec.mini.entity.sys.Role;
import com.tsingtec.mini.entity.sys.SysLog;
import com.tsingtec.mini.service.MenuService;
import com.tsingtec.mini.service.RoleService;
import com.tsingtec.mini.service.SysLogService;
import com.tsingtec.mini.vo.req.sys.log.SysLogPageReqVO;
import com.tsingtec.mini.vo.resp.sys.menu.MenuRespNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

@SpringBootTest
class MiniApplicationTests {

    @Autowired
    private SysLogService logService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Test
    void contextLoads() {
        /*SysLogPageReqVO sysLogPageReqVO = new SysLogPageReqVO();
        sysLogPageReqVO.setOperation("街道管理");
        Page<SysLog> logs = logService.pageInfo(sysLogPageReqVO);
        List<SysLog> results = logs.getContent();
        for(SysLog log:results){
            System.out.println(log.getId());
        }
        System.out.println(logs.getTotalElements());
        System.out.println(logs.getTotalPages());
        System.out.println(logs.getContent().size());
        System.out.println(logs.getNumber());*/


        Role role = roleService.findById(1);
        for(Menu menu:role.getMenus()){
            System.out.println(menu.getName());
        }

    }

}

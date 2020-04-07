package com.tsingtec.mini;

import com.tsingtec.mini.config.ConstantQiniu;
import com.tsingtec.mini.service.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import javax.annotation.Resource;

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


        System.out.println(StringUtils.join(cacheManager.getCacheNames(), ","));
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

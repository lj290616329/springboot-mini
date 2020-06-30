package com.tsingtec.mini;

import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.config.qiniu.ConstantQiniu;
import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.entity.mini.MaUser;
import com.tsingtec.mini.service.*;
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

    @Autowired
    private MpUserService mpUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ConstantQiniu constantQiniu;

    @Autowired
    private QuestionService questionService;

    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleServiceImpl ArticleServiceImpl;
    private final JsonMapper mapper = JsonMapper.nonEmptyMapper();

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private FriendService friendService;
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


        /*MpUser mpUser = new MpUser();
        mpUser.setOpenId("oypRptyfY2IgIVlfSThRAMvxnLGg");
        mpUserService.save(mpUser);*/

       /* MaUser maUser = maUserService.get(1);
        String token = jwtUtil.getToken(maUser);
        System.out.println(token);
        System.out.println(jwtUtil.verify(token));
        //System.out.println(jwtUtil.getClaim(token,"openid"));;
        System.out.println(jwtUtil.getClaim(token,"id"));;*/

    }

}

package com.tsingtec.mini;

import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.config.qiniu.ConstantQiniu;
import com.tsingtec.mini.service.*;
import com.tsingtec.mini.service.impl.ArticleServiceImpl;
import com.tsingtec.mini.vo.resp.websocket.FriendRespVO;
import com.vip.vjtools.vjkit.mapper.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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

        List<FriendRespVO> friends = friendService.getByUid(1);
        System.out.println(friends.toString());

        /*EvaluationDetailRespVO respVO = evaluationService.detail(1);
        System.out.println(respVO.toString());*/
        /*List<QuestionListRespVO> questionListRespVOS = questionService.getQuestionList();
        System.out.println(questionListRespVOS.toString());*/
        /*ArticlePageReqVO articlePageReqVO = new ArticlePageReqVO();
        articlePageReqVO.setPageSize(5);
        Page<Article> articles = articleService.pageInfo(articlePageReqVO);

        for (Article article:articles.getContent()){
            System.out.println(article.toString());
        }*/

        /*String userinfo = "{\"openId\":\"oWk8F5uPN-QZbwlMvfc-hGVq0H-A\",\"nickName\":\"妞，大爷来了\",\"gender\":\"1\",\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"Holy See (the) [Vatican City State]\",\"avatarUrl\":\"https://thirdwx.qlogo.cn/mmopen/vi_32/petsz7bmadgy178NEiaZRibYnA8BD3iaHkRNhYNNkgNU3TTej6IVCNbfp4x8x0ZX67TbYWAuiaj97s2tq5HMAxuLGQ/132\",\"unionId\":\"ofkfww9S7ahbdtIaQyOpQMSrzHXc\",\"watermark\":{\"timestamp\":\"1592301945\",\"appid\":\"wxffbd431d1affeacc\"}}";

        MiniUserInfoRespVO miniUserInfoRespVO = mapper.fromJson(userinfo,MiniUserInfoRespVO.class);
        System.out.println(miniUserInfoRespVO.toString());*/
        /*for (int i = 101; i < 200; i++) {
            ArticleAddReqVO article = new ArticleAddReqVO();
            article.setTitle("标题"+i);
            article.setTags("标签"+i);
            article.setPic("pic"+i);
            article.setContent("content"+i);
            article.setAid(1);
            articleService.save(article);
        }*/
        /*ArticleAddReqVO article = new ArticleAddReqVO();
        article.setTitle("标题102");
        article.setTags("标签101");
        article.setPic("pic101");
        article.setContent("content101");
        article.setAid(1);
        articleService.save(article);
*/

        /*List<ArticleSortReqVO> sorts = new ArrayList<>();
        ArticleSortReqVO articleSortReqVO1 = new ArticleSortReqVO();
        articleSortReqVO1.setId(100);
        articleSortReqVO1.setSort(99);
        sorts.add(articleSortReqVO1);
        ArticleSortReqVO articleSortReqVO2 = new ArticleSortReqVO();
        articleSortReqVO2.setId(99);
        articleSortReqVO2.setSort(100);
        sorts.add(articleSortReqVO2);

        articleService.sort(sorts);*/

        //System.out.println(ArticleServiceImpl.maxId());


        /*String password = "tsingtec1218";
        String data = "root";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(null);
        config.setProviderClassName(null);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        System.out.println(encryptor.encrypt(data));*/

        //System.out.println(StringUtils.join(cacheManager.getCacheNames(), ","));
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

package com.tsingtec.mini.controller.mini;

import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.service.EvaluationService;
import com.tsingtec.mini.service.MpUserService;
import com.tsingtec.mini.service.SubscribeMessageService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import com.tsingtec.mini.utils.MiniSendSubscribeMessage;
import com.tsingtec.mini.vo.req.mini.evaluation.EvaluationAddReqVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationDetailRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationListRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationResultResVO;
import com.tsingtec.mini.vo.resp.app.question.QuestionRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author lj
 * @Date 2020/6/23 15:11
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wxs/evaluation")
@Api(tags = "小程序模块--测评管理")
public class EvaluationMiniController {

    @Autowired
    private MpUserService mpUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private SubscribeMessageService subscribeMessageService;

    @PostMapping("/form")
    @ApiOperation(value = "获取聊天处理器主面板列表信息")
    public DataResult<String> form(@RequestBody @Valid List<QuestionRespVO> vo){
        DataResult<String> result = DataResult.success();
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        EvaluationAddReqVO addReqVO = new EvaluationAddReqVO();
        addReqVO.setMpUser(mpUser);
        addReqVO.setVos(vo);
        EvaluationResultResVO evaluationResultResVO = evaluationService.save(addReqVO);
        result.setData(evaluationResultResVO.getSrc());

        new Thread(){
            public void run(){
                String tmpIds = subscribeMessageService.getTmpIds(mpUser.getId());
                if(null!=tmpIds && tmpIds.contains("Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE")){
                    try {
                        MiniSendSubscribeMessage.sendFormSuccess(mpUser.getMiniOpenid(),evaluationResultResVO.getId());
                    } catch (WxErrorException e) {
                        System.out.println(e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return result;
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取个人测评记录")
    public DataResult<Map<String,List<EvaluationListRespVO>>> list(){
        DataResult<Map<String,List<EvaluationListRespVO>>> result = DataResult.success();
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        result.setData(evaluationService.fingByUid(mpUser.getId()));
        return result;
    }

    @GetMapping("detail/{id}")
    @ApiOperation(value = "获取聊天处理器主面板列表信息")
    public DataResult<EvaluationDetailRespVO> detail(@PathVariable("id")Integer id){
        DataResult<EvaluationDetailRespVO> result = DataResult.success();
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        EvaluationDetailRespVO vos = evaluationService.detail(id);
        if(!vos.getInformation().getMpUser().getId().equals(mpUser.getId())){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"您没有权限访问此信息!");
        }
        result.setData(vos);
        return result;
    }


}

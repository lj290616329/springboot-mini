package com.tsingtec.mini.controller.mini;

import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.service.DoctorService;
import com.tsingtec.mini.service.EvaluationService;
import com.tsingtec.mini.service.MpUserService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import com.tsingtec.mini.vo.req.mini.doctor.DoctorReqVO;
import com.tsingtec.mini.vo.req.mini.doctor.PatientReqVO;
import com.tsingtec.mini.vo.resp.app.doctor.DoctorRespVO;
import com.tsingtec.mini.vo.resp.app.doctor.PatientRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationDetailRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationListRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author lj
 * @Date 2020/6/27 17:33
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wxs/doctor")
@Api(tags = "小程序模块--医生模块")
public class DoctorMiniController {
    @Autowired
    private MpUserService mpUserService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private EvaluationService evaluationService;


    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("detail")
    @ApiOperation(value = "获取医生个人信息")
    public DataResult<DoctorRespVO> detail(){
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        DataResult<DoctorRespVO> result = DataResult.success();
        DoctorRespVO doctorRespVO = doctorService.findByUid(mpUser.getId());
        result.setData(doctorRespVO);
        return result;
    }

    @PostMapping("form")
    @ApiOperation(value = "修改医生个人信息")
    public DataResult form(@RequestBody @Valid DoctorReqVO vo){
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        DataResult result = DataResult.success();
        doctorService.update(mpUser.getId(),vo);
        return result;
    }

    @PostMapping("patient")
    @ApiOperation(value = "医生获取病人信息")
    public DataResult<List<PatientRespVO>> patient(@RequestBody(required = false) String name){
        DataResult<List<PatientRespVO>> result = DataResult.success();
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        if(StringUtils.isEmpty(name)){
            name = "";
        }
        List<PatientRespVO> vos = evaluationService.fingByUidAndName(mpUser.getId(),name);
        result.setData(vos);
        return result;
    }


    @PostMapping("list")
    @ApiOperation(value = "根据条件获取病人病历")
    public DataResult<Map<String,List<EvaluationListRespVO>>> list(@RequestBody PatientReqVO vo){
        DataResult<Map<String,List<EvaluationListRespVO>>> result = DataResult.success();
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        Map<String,List<EvaluationListRespVO>> vos = evaluationService.getListByPid(mpUser.getId(),vo);
        result.setData(vos);
        return result;
    }

    @GetMapping("detail/{id}")
    @ApiOperation(value = "获取病历详细")
    public DataResult<EvaluationDetailRespVO> detail(@PathVariable("id")Integer id){
        DataResult<EvaluationDetailRespVO> result = DataResult.success();
        EvaluationDetailRespVO vos = evaluationService.detail(id);
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        if(null==vos.getDoctor()||!vos.getDoctor().getMpUser().getId().equals(mpUser.getId())){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"您没有权限访问此信息!");
        }
        result.setData(vos);
        return result;
    }

    @PostMapping("bind")
    @ApiOperation(value = "医生获取病人信息")
    public DataResult<Integer> bind(@RequestBody(required = false) String src){
        DataResult<Integer> result = DataResult.success();
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        System.out.println(src);
        String id = jwtUtil.getClaim2(src,"qrCode");
        result.setData(evaluationService.bind(mpUser.getId(),Integer.valueOf(id)));
        return result;
    }
}

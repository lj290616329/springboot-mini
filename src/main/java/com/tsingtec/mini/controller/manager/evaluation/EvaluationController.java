package com.tsingtec.mini.controller.manager.evaluation;

import com.tsingtec.mini.aop.annotation.LogAnnotation;
import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.service.DoctorService;
import com.tsingtec.mini.service.EvaluationService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import com.tsingtec.mini.vo.req.mini.evaluation.EvaluationUpdateReqVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationDetailRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationListRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/23 15:11
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "应用模块--测评管理")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/evaluation")
    @ApiOperation(value = "分页获取测评信息列表接口")
    @LogAnnotation(title = "测评管理", action = "分页获取测评信息列表")
    public DataResult<List<EvaluationListRespVO>> pageInfo() {
        DataResult<List<EvaluationListRespVO>> result = DataResult.success();
        Doctor doctor = doctorService.findByAid(HttpContextUtils.getAid());
        if(doctor==null){
            return DataResult.getResult(BaseExceptionType.USER_ERROR,"您未绑定医生信息,请先绑定医生信息");
        }
        result.setData(evaluationService.pageInfo(doctor.getId()));
        return result;
    }


    @GetMapping("/evaluation/{id}")
    @ApiOperation(value = "获取测评详细接口")
    @LogAnnotation(title = "测评管理", action = "获取测评详细")
    public DataResult<EvaluationDetailRespVO> detail(@PathVariable("id")Integer id) {
        DataResult<EvaluationDetailRespVO> result = DataResult.success();
        result.setData(evaluationService.detail(id));
        return result;
    }

    @PutMapping("/evaluation")
    @ApiOperation(value = "修改测评接口")
    @LogAnnotation(title = "测评管理",action = "修改测评")
    public DataResult update(@RequestBody EvaluationUpdateReqVO vo){
        evaluationService.update(vo);
        return DataResult.success();
    }

}

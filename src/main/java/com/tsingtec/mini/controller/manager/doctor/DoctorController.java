package com.tsingtec.mini.controller.manager.doctor;

import com.tsingtec.mini.aop.annotation.LogAnnotation;
import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.service.DoctorService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.vo.req.mini.doctor.DoctorReqVO;
import com.tsingtec.mini.vo.req.mini.doctor.DoctorSetAdminReqVO;
import com.tsingtec.mini.vo.req.other.SwitchReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/7/1 11:25
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "应用模块--测评管理")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    
    @GetMapping("/doctor")
    @RequiresPermissions("manager:doctor:list")
    @ApiOperation(value = "分页获取医生列表接口")
    @LogAnnotation(title = "医生管理", action = "分页获取医生列表")
    public DataResult<List<Doctor>> pageInfo() {
        DataResult<List<Doctor>> result = DataResult.success();
        result.setData(doctorService.findList());
        return result;
    }

    @PostMapping("/doctor")
    @ApiOperation(value = "新增医生接口")
    @RequiresPermissions("manager:doctor:add")
    @LogAnnotation(title = "医生管理",action = "新增医生")
    public DataResult addUser(@RequestBody @Valid DoctorReqVO vo){
        doctorService.add(vo);
        return DataResult.success();
    }

    @PutMapping("/doctor")
    @ApiOperation(value = "修改医生账号接口")
    @RequiresPermissions("manager:doctor:edit")
    @LogAnnotation(title = "医生管理",action = "修改医生账号接口")
    public DataResult setAdmin(@RequestBody DoctorSetAdminReqVO vo){
        doctorService.setAdmin(vo);
        return DataResult.success();
    }

    @PutMapping("/doctor/{id}")
    @ApiOperation(value = "修改医生接口")
    @RequiresPermissions("manager:doctor:edit")
    @LogAnnotation(title = "医生管理",action = "修改医生")
    public DataResult update(@PathVariable("id")Integer id,@RequestBody DoctorReqVO vo){
        doctorService.updateById(id,vo);
        return DataResult.success();
    }

    //医生开关
    @PutMapping("/question/status")
    @ApiOperation(value = "医生开关接口")
    @RequiresPermissions("manager:doctor:edit")
    @LogAnnotation(title = "医生管理",action = "医生开关接口")
    public DataResult basic(@RequestBody SwitchReqVO vo){
        doctorService.status(vo);
        return DataResult.success();
    }



}

package com.tsingtec.mini.controller.manager.sys;

import com.tsingtec.mini.aop.annotation.LogAnnotation;
import com.tsingtec.mini.entity.sys.SysLog;
import com.tsingtec.mini.service.SysLogService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.vo.req.sys.log.SysLogPageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/manager/sys")
@Api(tags = "系统模块-系统操作日志管理")
@RestController
public class SysLogController {

    @Autowired
    private SysLogService logService;

    @GetMapping("/logs")
    @RequiresPermissions("sys:log:list")
    @ApiOperation(value = "分页查询系统操作日志接口")
    public DataResult<Page<SysLog>> pageInfo(SysLogPageReqVO vo){
        DataResult<Page<SysLog>> result = DataResult.success();
        result.setData(logService.pageInfo(vo));
        return result;
    }

    @DeleteMapping("/logs")
    @ApiOperation(value = "删除日志接口")
    @RequiresPermissions("sys:log:deleted")
    @LogAnnotation(title = "系统操作日志管理",action = "删除系统操作日志")
    public DataResult deleted(@RequestBody List<Integer> logIds){
        logService.delete(logIds);
        return DataResult.success();
    }
}

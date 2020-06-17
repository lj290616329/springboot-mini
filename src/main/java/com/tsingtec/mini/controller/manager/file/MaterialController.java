package com.tsingtec.mini.controller.manager.file;

import com.tsingtec.mini.aop.annotation.LogAnnotation;
import com.tsingtec.mini.entity.file.Material;
import com.tsingtec.mini.service.MaterialService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.vo.req.material.MaterialPageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/4/9 14:08
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "组织模块-用户管理")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/materials")
    @RequiresPermissions("sys:material:list")
    @ApiOperation(value = "分页获取文档列表接口")
    @LogAnnotation(title = "文档管理", action = "分页获取文档列表")
    public DataResult<Page<Material>> pageInfo(MaterialPageReqVO vo) {
        DataResult<Page<Material>> result = DataResult.success();
        result.setData(materialService.pageInfo(vo));
        return result;
    }

    @PostMapping("/material")
    @ApiOperation(value = "新增文档接口")
    @RequiresPermissions("sys:material:add")
    @LogAnnotation(title = "文档管理",action = "新增文档")
    public DataResult addMaterial(@RequestBody @Valid Material vo){
        materialService.save(vo);
        return DataResult.success();
    }

    @DeleteMapping("/material")
    @ApiOperation(value = "删除文档接口")
    @RequiresPermissions("sys:material:add")
    @LogAnnotation(title = "文档管理",action = "删除文档")
    public DataResult delMaterial(@RequestBody @ApiParam(value = "文档id集合") List<Integer> mids){
        materialService.deleteBatch(mids);
        return DataResult.success();
    }

}

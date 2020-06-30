package com.tsingtec.mini.controller.mini;

import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.service.InformationService;
import com.tsingtec.mini.service.MpUserService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import com.tsingtec.mini.vo.req.mini.information.InformationReqVO;
import com.tsingtec.mini.vo.resp.app.mini.InformationRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author lj
 * @Date 2020/6/8 15:32
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wxs/information")
@Api(tags = "公众号模块--客服管理")
public class InformationMiniController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private MpUserService mpUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/detail")
    @ApiOperation(value = "获取个人信息")
    public DataResult<InformationRespVO> detail(){
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        DataResult<InformationRespVO> result = DataResult.success();
        InformationRespVO informationReqVO = informationService.findByUid(mpUser.getId());
        result.setData(informationReqVO);
        return result;
    }

    @PostMapping("/form")
    @ApiOperation(value = "修改个人信息")
    public DataResult form(@RequestBody @Valid InformationReqVO vo){
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        DataResult result = DataResult.success();
        informationService.save(mpUser,vo);
        mpUser.setName(vo.getName());
        mpUser.setPhone(vo.getPhone());
        mpUserService.save(mpUser);
        return result;
    }

}

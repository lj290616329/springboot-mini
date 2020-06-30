package com.tsingtec.mini.controller.mini;

import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.service.MpUserService;
import com.tsingtec.mini.service.SubscribeMessageService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lj
 * @Date 2020/6/29 17:01
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wxs/subscribe")
@Api(tags = "小程序模块--订阅管理")
public class SubscribeMessageMiniController {

    @Autowired
    private SubscribeMessageService subscribeMessageService;

    @Autowired
    private MpUserService mpUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("form")
    @ApiOperation(value = "获取订阅消息")
    public DataResult form(@RequestBody(required = false) String tmplIds){
        DataResult result = DataResult.success();
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        if(StringUtils.isEmpty(tmplIds)){
            subscribeMessageService.deleteByUid(mpUser.getId());
        }else {
            subscribeMessageService.save(mpUser.getId(),tmplIds);
        }
        return result;
    }
}

package com.tsingtec.mini.controller.mp;

import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.service.MpUserService;
import com.tsingtec.mini.utils.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@Api(tags = "微信用户接口")
@RequestMapping("/mobile")
public class WxMpUserController {

    @Autowired
    private MpUserService mpUserService;

    @GetMapping("user_detail")
    public String detail(){
        return "mp/detail";
    }

    /**
     * 获取用户
     */
    @ResponseBody
    @GetMapping("/user/{id}")
    @ApiOperation(value = "用户登录接口")
    public DataResult<MpUser> user(@PathVariable("id") Integer id) {
        DataResult result = DataResult.success();
        MpUser mpUser = mpUserService.get(id);
        System.out.println(mpUser);
        result.setData(mpUser);
        return result;
    }

}
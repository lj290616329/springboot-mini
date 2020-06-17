package com.tsingtec.mini.controller.mp;

import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.entity.sys.Admin;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.service.AdminService;
import com.tsingtec.mini.service.DoctorService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import com.tsingtec.mini.vo.req.free.FreeLoginReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author lj
 * @Date 2020/6/11 14:19
 * @Version 1.0
 */
@Slf4j
@Controller
@Api(tags = "微信登录接口")
@RequestMapping("/mobile")
public class FreeLoginMobileController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AdminService adminService;

    @GetMapping("free_login/{token:.+}")
    public String freeLogin(@PathVariable("token") String token, Model model){
        System.out.println(token);
        if(!jwtUtil.verify(token)){
            model.addAttribute("msg","二维码过期,请重新生成后,再进行...");
            return "mp/error/403";
        }
        MpUser mpUser = (MpUser) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("mp_user");
        Doctor doctor = doctorService.findByMpUser(mpUser);
        if(null == doctor){
            model.addAttribute("msg","您没有权限进行此操作!");
            return "mp/error/403";
        };
        model.addAttribute("token",token);
        return "mp/free/login";
    }

    @ResponseBody
    @PostMapping("free_login/verify")
    public DataResult verify(@RequestBody @Valid FreeLoginReqVO vo){
        if(!jwtUtil.verify(vo.getToken())){
            return DataResult.getResult(BaseExceptionType.TOKEN_ERROR,"二维码过期,请重新生成后,再进行");
        }
        MpUser mpUser = (MpUser) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("mp_user");
        Doctor doctor = doctorService.findByMpUser(mpUser);
        if(null == doctor){
            return DataResult.getResult(BaseExceptionType.TOKEN_ERROR,"您没有权限进行此操作!");
        };
        Admin admin = adminService.findById(doctor.getAid());
        if(admin.UNVALID.equals(admin.getStatus())){
            return DataResult.getResult(BaseExceptionType.TOKEN_ERROR,"您绑定的账号已被禁用,请联系管理员!");
        }
        String sessionid = jwtUtil.getClaim(vo.getToken(),"sessionid");
        if (vo.getAggre()){
            jwtUtil.createAgree(sessionid,admin.getLoginName());
        }else{
            jwtUtil.createAgree(sessionid,"");
        }
        return DataResult.success();
    };
}

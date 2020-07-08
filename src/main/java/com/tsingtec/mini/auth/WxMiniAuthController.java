package com.tsingtec.mini.auth;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.config.mini.WxMaConfiguration;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.service.DoctorService;
import com.tsingtec.mini.service.InformationService;
import com.tsingtec.mini.service.MpUserService;
import com.tsingtec.mini.utils.BeanUtil;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.vo.req.mini.WxLoginReqVO;
import com.tsingtec.mini.vo.resp.app.doctor.DoctorRespVO;
import com.tsingtec.mini.vo.resp.app.mini.MiniUserInfoRespVO;
import com.tsingtec.mini.vo.resp.base.BaseUserRespVO;
import com.vip.vjtools.vjkit.mapper.JsonMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@RestController
@Api(tags = "小程序接口")
@RequestMapping("/mini")
public class WxMiniAuthController {

    @Autowired
    private MpUserService mpUserService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private DoctorService doctorService;


    @Autowired
    private JwtUtil jwtUtil;

    private final JsonMapper mapper = JsonMapper.nonEmptyMapper();

    /**
     * 授权 获取身份信息
     * @param wxLoginVo
     * @return
     */
    @PostMapping("/auth")
    @ApiOperation(value = "用户授权接口")
    public DataResult<BaseUserRespVO> sign(@RequestBody WxLoginReqVO wxLoginVo) throws JsonProcessingException {
        final WxMaService wxService = WxMaConfiguration.getMaService();
        DataResult<BaseUserRespVO> result = DataResult.success();

        log.info("登录信息为:{}",wxLoginVo);
        String code = wxLoginVo.getCode();
        if(StringUtils.isBlank(code)){
            return DataResult.fail("授权信息不全,请重新进行授权");
        }
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);

            if (!wxService.getUserService().checkUserInfo(session.getSessionKey(), wxLoginVo.getRawData(), wxLoginVo.getSignature())) {
                return DataResult.fail("user check failed");
            }
            // 解密用户信息
            WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(session.getSessionKey(), wxLoginVo.getEncryptedData(), wxLoginVo.getIv());

            MiniUserInfoRespVO miniUserInfoRespVO = mapper.fromJson(mapper.toJson(userInfo),MiniUserInfoRespVO.class);

            System.out.println(miniUserInfoRespVO.toString());

            log.info(userInfo.toString());

            MpUser mpUser = new MpUser();

            BeanUtil.copyPropertiesIgnoreNull(miniUserInfoRespVO, mpUser);

            mpUser = mpUserService.save(mpUser);

            String token = jwtUtil.getToken(mpUser);
            Boolean ifAuth = informationService.ifAuth(mpUser.getId());
            Integer type = 1;
            DoctorRespVO doctor = doctorService.findByUid(mpUser.getId());
            if(null != doctor){
                type = 2;
            }
            result.setData(new BaseUserRespVO(type,ifAuth,token));
        }catch (WxErrorException e) {
            return DataResult.fail("授权失败,请稍后再试!");
        }
        return result;
    }



    /**
     * 登录接口
     */
    @GetMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult<BaseUserRespVO> login(String code) {
        DataResult<BaseUserRespVO> result = DataResult.success();
        if (StringUtils.isBlank(code)) {
            return DataResult.fail("授权信息不全,请重新进行授权!");
        }
        final WxMaService wxService = WxMaConfiguration.getMaService();
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info(session.toString());
            MpUser mpUser = mpUserService.findByUnionId(session.getUnionid());
            if(null != mpUser){
                String token = jwtUtil.getToken(mpUser);

                Boolean ifAuth = informationService.ifAuth(mpUser.getId());
                Integer type = 1;
                DoctorRespVO doctor = doctorService.findByUid(mpUser.getId());
                if(null != doctor){
                    type = 2;
                }
                result.setData(new BaseUserRespVO(type,ifAuth,token));
            }else{
                return DataResult.fail("登录失败!");
            }
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return DataResult.fail("登录失败!");
        }
        return result;
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public DataResult phone(String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        DataResult result = DataResult.success();
        final WxMaService wxService = WxMaConfiguration.getMaService();
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return result;
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return result;
    }
}
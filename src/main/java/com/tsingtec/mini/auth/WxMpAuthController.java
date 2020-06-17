package com.tsingtec.mini.auth;

import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.service.MpUserService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 公众号授权接口逻辑
 */
@Controller
@RequestMapping(value = "/mp")
public class WxMpAuthController {

	@Value("${tsingcloud.weixin.domain}")
	private String domain;

	@Autowired
	private WxMpService wxService;

	@Autowired
	private MpUserService mpUserService;

	@GetMapping(value="auth")
	public String auth(HttpServletRequest request){
		String redirectUrl = "";
		String authUrl = request.getQueryString();
		if (authUrl != null && authUrl.contains("authUrl=")) {
			authUrl = authUrl.replace("authUrl=", "");
			redirectUrl = auth2Url(domain + "/mp/token?auth2url="+domain + authUrl);
		}
		return "redirect:" + redirectUrl;
	}

	@GetMapping(value="token")
	public String token(HttpServletRequest request, @RequestParam String code) throws WxErrorException {

		HttpSession session = request.getSession();

		String lastUrl =  request.getQueryString();

		if(lastUrl != null && lastUrl.contains("auth2url=")) {
			lastUrl = lastUrl.replace("auth2url=", "");
			if(!lastUrl.contains("?")){
				lastUrl = lastUrl.replace("&code=", "?code=");
			}
		}

		WxMpOAuth2AccessToken accessToken = wxService.oauth2getAccessToken(code);

		WxMpUser wxMpUser = wxService.getUserService().userInfo(accessToken.getOpenId(), null);

		MpUser mpUser = new MpUser();

		BeanUtils.copyProperties(wxMpUser,mpUser);

		mpUser = mpUserService.save(mpUser);

		session.setAttribute("mp_user", mpUser);

		return "redirect:" + lastUrl;

	}

	/**
	 * 组装request参数
	 * @return
	 */
	private String auth2Url(String url) {
		String oauth2Url = wxService.oauth2buildAuthorizationUrl(url,WxConsts.OAuth2Scope.SNSAPI_USERINFO,null);
		return oauth2Url;
	}
}


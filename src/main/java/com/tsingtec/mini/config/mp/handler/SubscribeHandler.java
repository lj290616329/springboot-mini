package com.tsingtec.mini.config.mp.handler;

import com.tsingtec.mini.config.mp.builder.TextBuilder;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.service.AdminService;
import com.tsingtec.mini.service.DoctorService;
import com.tsingtec.mini.service.MpUserService;
import com.tsingtec.mini.utils.BeanUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class SubscribeHandler extends AbstractHandler {
	
	@Autowired
	private MpUserService mpUserService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AdminService adminService;
	
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {
    	this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());
        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

            if (userWxInfo != null) {
            	MpUser mpUser = new MpUser();
                BeanUtil.copyPropertiesIgnoreNull(userWxInfo, mpUser);
    			mpUserService.save(mpUser);
            }

        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                this.logger.info("该公众号没有获取用户信息权限！");
            }
        }

        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        responseResult.setToUserName(wxMessage.getFromUser());
        responseResult.setFromUserName(wxMessage.getToUser());
        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("欢迎关注“清云健康”", wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     	* 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
        throws Exception {
        //return WxMpXmlOutMessage.TEXT().content("content").build();
        /*String eventKey = wxMessage.getEventKey();
        String unionId = wxMessage.getUnionId();
        //判断是否是推广二维码
        if (eventKey.contains("qrscene_")) {
            eventKey = eventKey.replaceFirst("qrscene_", "");
            //数字统一认定为绑定账号所用
            if(StringUtils.isNumeric(eventKey)){
                Integer aid = Integer.valueOf(eventKey);
                MpUser mpUser = mpUserService.findByUnionId(unionId);

                Admin admin = adminService.findById(aid);
                if(null==admin){
                    return new TextBuilder().build("你要绑定的账号不存在,请联系管理员处理!", wxMessage, weixinService);
                }

                Doctor doctor = doctorService.findByAid(aid);

                if(null == doctor) {
                    //扫描的账号已经有人绑定了 而且绑定的还不是自己
                    if(!doctor.getMpUser().getUnionId().equals(weixinService.) && !admin.getUnionId().equals(mpUser.getId()) ) {
                        textMessage.setContent("该账号已绑定其他管理员,请联系管理员处理!");
                        textMessage.setMsgType(MsgTypes.TEXT.getType());
                        return textMessage;
                    }

                    warden = new Doctor();
                    warden.setAid(aid);
                    warden.setMpUser(mpUser);
                    wardenService.save(warden);

                    textMessage.setContent("绑定登陆账号成功!");

                    admin.setUnionId(mpUser.getId());
                    admin.setState(Constants.INITIAL);
                }else{
                    //用户绑定的是1 扫描的是2,所以需要重新获取一次绑定的账号
                    admin = adminService.getAdmin(warden.getAid());
                    if(admin.getRole().getId().equals(3L)) {
                        admin.setState(Constants.DELETE);
                    }
                    admin.setUnionId(0L);
                    wardenService.delete(warden);
                    textMessage.setContent("取消绑定登陆账号成功!");
                }
                adminService.updateAdmin(admin);
                textMessage.setMsgType(MsgTypes.TEXT.getType());
                return textMessage;
            }

            if(StringUtils.isNotBlank(eventKey)) {
                AutoReply autoReply = autoReplyService.findByName(eventKey);

                if(autoReply!=null) {

                    if(autoReply.getType().equals(MsgTypes.TEXT.getType())) {
                        textMessage.setContent(autoReply.getDescription());
                        textMessage.setMsgType(MsgTypes.TEXT.getType());
                        return textMessage;
                    }

                    if(autoReply.getType().equals(MsgTypes.NEWS.getType())) {
                        NewsMessage newsMessage = new NewsMessage();
                        Articles articles = new Articles();
                        articles.setTitle(autoReply.getTitle());
                        articles.setDescription(autoReply.getDescription());
                        articles.setPicUrl(autoReply.getPic());
                        articles.setUrl(autoReply.getUrl());
                        dtoList.add(articles);
                        newsMessage.setArticles(dtoList);
                        return newsMessage;
                    }
                }
            }
        }*/

        //TODO
        return null;
    }

}

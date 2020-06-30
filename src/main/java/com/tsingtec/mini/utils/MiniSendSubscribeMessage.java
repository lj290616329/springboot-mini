package com.tsingtec.mini.utils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.tsingtec.mini.config.mini.WxMaConfiguration;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/29 17:26
 * @Version 1.0
 */
public class MiniSendSubscribeMessage {

    public static void sendFormSuccess(String meopenid,Integer id) throws WxErrorException {
        final WxMaService wxService = WxMaConfiguration.getMaService();
        WxMaSubscribeMessage wxMaTemplateMessage = new WxMaSubscribeMessage();
        wxMaTemplateMessage.setToUser(meopenid);
        wxMaTemplateMessage.setTemplateId("Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE");
        wxMaTemplateMessage.setPage("/pages/personal/evaluation_detail?id="+id);
        List<WxMaSubscribeMessage.Data> data = new ArrayList<>();
        data.add(new WxMaSubscribeMessage.Data("thing2","您已成功提交检测信息"));
        data.add(new WxMaSubscribeMessage.Data("thing4","点击可查看详细信息"));
        wxMaTemplateMessage.setData(data);
        wxService.getMsgService().sendSubscribeMsg(wxMaTemplateMessage);
    }
}

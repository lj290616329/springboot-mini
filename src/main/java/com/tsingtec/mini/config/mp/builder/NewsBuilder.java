package com.tsingtec.mini.config.mp.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/5/25 10:37
 * @Version 1.0
 */
public class NewsBuilder {
    public WxMpXmlOutNewsMessage build(List<WxMpXmlOutNewsMessage.Item> articles, WxMpXmlMessage wxMessage, WxMpService service) {
        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS().articles(articles).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
        return m;
    }
}

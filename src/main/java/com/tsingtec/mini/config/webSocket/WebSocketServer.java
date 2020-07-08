package com.tsingtec.mini.config.webSocket;

import com.google.common.collect.Lists;
import com.tsingtec.mini.entity.websocket.Chatlog;
import com.tsingtec.mini.service.ChatIdService;
import com.tsingtec.mini.service.ChatlogService;
import com.tsingtec.mini.utils.BeanMapper;
import com.tsingtec.mini.vo.req.websocket.MessageReqVO;
import com.tsingtec.mini.vo.resp.websocket.ChatMsgRespVO;
import com.vip.vjtools.vjkit.mapper.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/websocket/{uid}/{brief}")
public class WebSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;

    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<Integer,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;

    /**接收userId*/
    private Integer uid;

    private final JsonMapper mapper = JsonMapper.nonEmptyMapper();

    static ChatlogService chatlogService;

    @Autowired
    public void setChatlogService(ChatlogService chatlogService){
        WebSocketServer.chatlogService = chatlogService;
    }

    static ChatIdService chatIdService;

    @Autowired
    public void setChatIdService(ChatIdService chatIdService){
        WebSocketServer.chatIdService = chatIdService;
    }

    /**
     * brief 是否极简模式,非极简模式下将未读信息一股脑都发送出去~
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam(value = "uid") Integer uid,@PathParam(value = "brief") Boolean brief) {
        this.session = session;
        this.uid = uid;
        if(webSocketMap.containsKey(uid)){
            webSocketMap.remove(uid);
            webSocketMap.put(uid,this);
            //加入set中
        }else{
            webSocketMap.put(uid,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        log.info("用户连接:"+uid+",当前在线人数为:" + getOnlineCount());
        try {
            sendMessage("{\"type\":\"success connect\"}");
        } catch (IOException e) {
            log.error("用户:"+uid+",网络异常!!!!!!");
        }
        if(!brief){
            List<Integer> toids = Lists.newArrayList(uid);
            List<Chatlog> chatlogs = chatlogService.findByToidInAndStatus(toids,false);
            chatlogs.forEach(c ->{
                MessageReqVO m = BeanMapper.map(c, MessageReqVO.class);
                try {
                    sendMessage(m.toString());
                    chatlogService.update(c);
                } catch (IOException e) {
                    e.printStackTrace();
                };
            });
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(uid)){
            webSocketMap.remove(uid);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+uid+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:"+uid+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(!StringUtils.isEmpty(message)){
            try {
                //解析发送的报文
                ChatMsgRespVO chatMsgRespVO = mapper.fromJson(message,ChatMsgRespVO.class);
                if(chatMsgRespVO.getType().equals("chatMessage")){
                    MessageReqVO messageReqVO = chatMsgRespVO.getData();
                    Integer chatid = chatIdService.getByToidAndFromId(messageReqVO.getToid(), messageReqVO.getFromid());

                    messageReqVO.setChatid(chatid);
                    Chatlog chatlog = new Chatlog();
                    chatlog = BeanMapper.map(messageReqVO,Chatlog.class);
                    chatlog.setFromid(this.uid);

                    if(!chatlog.getToid().equals(this.uid) && webSocketMap.containsKey(chatlog.getToid())){
                        webSocketMap.get(chatlog.getToid()).sendMessage(messageReqVO.toString());
                        chatlog.setStatus(true);
                    }
                    chatlogService.save(chatlog);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.uid+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 遍历群发消息
     * 保持不掉线
     * @param text
     */
    public static void send(String text)  throws IOException {
        for (Map.Entry<Integer, WebSocketServer> entry : webSocketMap.entrySet()) {
            System.out.println("群发消息给"+entry.getKey()+"消息为:"+text);
            webSocketMap.get(entry.getKey()).sendMessage(text);
        }
    }

    /**
     * 发送自定义消息
     * */
    public static Boolean sendInfo(String message,@PathParam("uid") Integer uid) throws IOException {
        log.info("发送消息到:"+uid+"，报文:"+message);
        if(!StringUtils.isEmpty(uid) && webSocketMap.containsKey(uid)){
            webSocketMap.get(uid).sendMessage(message);
            return true;
        }else{
            log.error("用户"+uid+",不在线！");
            return false;
        }
    }

    public static List<Integer> onlineKey(){
        List<Integer> keys = new LinkedList<>();
        webSocketMap.entrySet().stream().forEachOrdered(e -> keys.add(e.getKey()));
        return keys;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}


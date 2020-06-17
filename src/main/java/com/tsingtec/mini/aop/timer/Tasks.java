package com.tsingtec.mini.aop.timer;

import com.tsingtec.mini.config.webSocket.WebSocketServer;
import com.tsingtec.mini.entity.websocket.Chatlog;
import com.tsingtec.mini.service.ChatlogService;
import com.tsingtec.mini.utils.BeanMapper;
import com.tsingtec.mini.vo.req.websocket.MessageReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/3/21 14:23
 * @Version 1.0
 * 定时任务
 */
@Component
@EnableScheduling
@EnableAsync
public class Tasks {

    @Autowired
    private ChatlogService chatlogService;

    /**
     * 定时统计
     * 3,6,9,12月的1,2,3日1点1分1秒进行一次统计(一个月三次只是为了防止未进行统计)
     * https://qqe2.com/cron 在线生成cron
     */
    @Scheduled(cron = "1 1 1 1,2,3 3,6,9,12 ? ")
    public void statistics(){

    }

    @Async
    @Scheduled(fixedRate=55*1000)
    public void configureTasks() throws Exception{
        if(WebSocketServer.getOnlineCount()>0){
            List<Integer> toids = WebSocketServer.onlineKey();
            List<Chatlog> chatlogs = chatlogService.findByToidInAndStatus(toids,false);
            chatlogs.forEach(c ->{
                MessageReqVO m = BeanMapper.map(c, MessageReqVO.class);
                try {
                    if(WebSocketServer.sendInfo(m.toString(),c.getToid())){
                        chatlogService.update(c);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                };
            });
            WebSocketServer.send("{\"type\":\"heartBeat\",\"msg\":\"呼叫动拐,呼叫动拐!\"}");
        }
    }


}

package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.websocket.Chatlog;
import com.tsingtec.mini.repository.ChatLogRepository;
import com.tsingtec.mini.service.ChatlogService;
import com.tsingtec.mini.utils.BeanMapper;
import com.tsingtec.mini.vo.resp.websocket.ChatlogRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class ChatlogServiceImpl implements ChatlogService {

    @Autowired
    private ChatLogRepository chatLogRepository;

    @Override
    public void save(Chatlog chatlog) {
        chatLogRepository.save(chatlog);
    }

    @Override
    public void update(Chatlog chatlog) {
        chatlog.setStatus(true);
        chatLogRepository.save(chatlog);
    }

    @Override
    public List<Chatlog> findByToidInAndStatus(List<Integer> toids,Boolean status) {
        return chatLogRepository.findByToidInAndStatus(toids,status);
    }

    @Override
    public List<Chatlog> findByFromidAndToidAndStatus(Integer fromid, Integer toid, Boolean status) {
        return chatLogRepository.findByFromidAndToidAndStatus(fromid,toid,status);
    }

    @Override
    public List<ChatlogRespVO> getChatLogByChatid(Integer uid, Integer chatid) {
        List<Chatlog> chatlogs = chatLogRepository.getChatlogByChatidOrderByCreateTimeDesc(chatid);
        chatlogs.parallelStream().forEach((t)->
                t.setMine(t.getFromid().equals(uid))
        );
        List<ChatlogRespVO> chatlogRespVOS = BeanMapper.mapList(chatlogs, ChatlogRespVO.class);
        System.out.println(chatlogRespVOS);
        return chatlogRespVOS;
    }

    /**
     * 每个对话获取最后20条记录,主要用于公众号和web管理后台
     * @param fromid
     * @param chatids
     * @return
     */
    @Override
    public Map<String,List<ChatlogRespVO>> findByChatidInLimit(Integer fromid,List<Integer> chatids) {
        List<Chatlog> chatlogs = chatLogRepository.findByChatidInLimit(chatids);
        chatlogs.parallelStream().forEach((t)->
                t.setMine(t.getFromid().equals(fromid))
        );
        List<ChatlogRespVO> chatlogRespVOS = BeanMapper.mapList(chatlogs, ChatlogRespVO.class);

        return chatlogRespVOS.stream().collect(Collectors.groupingBy(s -> "friend"+s.getChatid()));
    }

    /**
     * 每个对话获取所有记录,主要用于小程序,直接展示所有的记录
     * @param fromid
     * @param chatids
     * @return
     */
    @Override
    public Map<String,List<ChatlogRespVO>> findByChatids(Integer fromid,List<Integer> chatids) {
        List<Chatlog> chatlogs = chatLogRepository.findByChatidIn(chatids);
        chatlogs.parallelStream().forEach((t)->
                t.setMine(t.getFromid().equals(fromid))
        );
        List<ChatlogRespVO> chatlogRespVOS = BeanMapper.mapList(chatlogs, ChatlogRespVO.class);

        return chatlogRespVOS.stream().collect(Collectors.groupingBy(s -> "friend"+s.getChatid()));
    }

}

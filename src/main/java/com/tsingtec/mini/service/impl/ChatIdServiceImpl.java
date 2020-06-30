package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.websocket.ChatId;
import com.tsingtec.mini.repository.ChatIdRepository;
import com.tsingtec.mini.service.ChatIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class ChatIdServiceImpl implements ChatIdService {

    @Autowired
    private ChatIdRepository chatIdRepository;

    private String ids(Integer toid, Integer fromid) {
        if(toid>fromid){
            return "#"+fromid+"#"+toid + "#";
        }
        return "#"+toid+"#"+fromid + "#";
    }

    private ChatId findByIds(String ids){
        return chatIdRepository.findByIds(ids);
    }

    @Override
    @Cacheable(value="data",key="'chatId'+#toid+#fromid")
    public Integer getByToidAndFromId(Integer toid, Integer fromid) {
        String ids = ids(toid,fromid);
        ChatId chatId = findByIds(ids);
        if(chatId==null){
            chatId = new ChatId();
            chatId.setIds(ids);
            chatIdRepository.save(chatId);
        }
        return chatId.getId();
    }

    @Override
    public List<Integer> getIdByIdsLike(Integer uid) {
        return chatIdRepository.getIdByIdsLike("%#"+uid+"#%");
    }
}

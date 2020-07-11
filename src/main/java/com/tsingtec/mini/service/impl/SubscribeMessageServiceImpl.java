package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.mini.SubscribeMessage;
import com.tsingtec.mini.repository.SubscribeMessageRepository;
import com.tsingtec.mini.service.SubscribeMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author lj
 * @Date 2020/6/29 16:58
 * @Version 1.0
 */
@Service
public class SubscribeMessageServiceImpl implements SubscribeMessageService {
    @Autowired
    private SubscribeMessageRepository subscribeMessageRepository;

    @Override
    @Transactional
    public void save(Integer uid, String tmplIds) {
        SubscribeMessage subscribeMessage = subscribeMessageRepository.findByUid(uid);
        if(null==subscribeMessage){
            subscribeMessage = new SubscribeMessage();
        }
        subscribeMessage.setUid(uid);
        subscribeMessage.setTmpIds(tmplIds);
        subscribeMessageRepository.save(subscribeMessage);
    }

    @Override
    @Transactional
    public void deleteByUid(Integer uid) {
        subscribeMessageRepository.deleteByUid(uid);
    }

    @Override
    public String getTmpIds(Integer uid) {
        SubscribeMessage subscribeMessage = subscribeMessageRepository.findByUid(uid);
        if(null==subscribeMessage){
            return "";
        }
        return subscribeMessage.getTmpIds();
    }
}

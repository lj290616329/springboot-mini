package com.tsingtec.mini.service.impl;

import com.google.common.collect.Lists;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.entity.websocket.ChatId;
import com.tsingtec.mini.entity.websocket.Chatlog;
import com.tsingtec.mini.entity.websocket.Friend;
import com.tsingtec.mini.repository.ChatIdRepository;
import com.tsingtec.mini.repository.ChatLogRepository;
import com.tsingtec.mini.repository.FriendRepository;
import com.tsingtec.mini.service.FriendService;
import com.tsingtec.mini.utils.BeanMapper;
import com.tsingtec.mini.vo.req.websocket.SignReqVO;
import com.tsingtec.mini.vo.resp.websocket.FriendRespVO;
import com.tsingtec.mini.vo.resp.websocket.MineRespVO;
import com.tsingtec.mini.vo.resp.websocket.ToRespVO;
import com.vip.vjtools.vjkit.mapper.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class FriendServiceImpl implements FriendService {

    private final JsonMapper mapper = JsonMapper.nonEmptyMapper();
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private ChatIdRepository chatIdRepository;

    @Autowired
    private ChatLogRepository chatLogRepository;

    private String ids(Integer toid, Integer fromid) {
        if(toid>fromid){
            return "#"+fromid+"#"+toid + "#";
        }
        return "#"+toid+"#"+fromid + "#";
    }

    @Override
    public Friend checkByUidAndMode(Integer uid, String type) {
        return friendRepository.findByUidAndMode(uid,type);
    }

    @Override
    public MineRespVO getByUidAndMode(MpUser mpUser, String type) {
        Friend friend = checkByUidAndMode(mpUser.getId(),type);
        if(null==friend){
            friend = new Friend();
            friend.setAvatar(mpUser.getHeadImgUrl());
            friend.setType(type);
            friend.setUid(mpUser.getId());
            friend.setUsername(mpUser.getName());
            friendRepository.save(friend);
        }
        return BeanMapper.map(friend,MineRespVO.class);
    }

    @Override
    public List<FriendRespVO> getByUid(Integer uid) {
        List<Integer> fids = chatIdRepository.getFidsByUid("#"+uid+"#","%#"+uid+"#%");
        List<Friend> friends = friendRepository.findAllById(fids);
        //获取未读数量
        friends.forEach(friend -> {

            ChatId chatId = chatIdRepository.findByIds(ids(friend.getId(),uid));

            friend.setUnRead(chatLogRepository.countByChatidAndFromidAndStatus(chatId.getId(),friend.getId(),false));
            //获取最后收到的信息

            Chatlog chatlog = chatLogRepository.getDistinctFirstByChatidAndStatusOrderByIdDesc(chatId.getId(),true);
            if(null!=chatlog){
                friend.setContent(chatlog.getContent());
                friend.setHistoryTime(chatlog.getCreateTime());
            }
            //friend.setChatlog(chatLogRepository.getDistinctFirstByChatidAndStatusOrderByIdDesc(chatId.getId(),true));
        });
        /**
         * 只设置一个分组
         */
        FriendRespVO friendRespVO = new FriendRespVO();
        if(friends.size()>0){
            friendRespVO.setList(BeanMapper.mapList(friends,MineRespVO.class));
        }
        return Lists.newArrayList(friendRespVO);
    }

    @Override
    public void update(SignReqVO signReqVO) {
        Friend friend = friendRepository.getOne(signReqVO.getId());
        friend.setSign(signReqVO.getSign());
        friendRepository.save(friend);
    }

    @Override
    public ToRespVO get(Integer id) {
        Friend friend = friendRepository.getOne(id);
        return mapper.fromJson(mapper.toJson(friend),ToRespVO.class);
    }
}

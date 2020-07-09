package com.tsingtec.mini.controller.manager.chat;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tsingtec.mini.aop.annotation.LogAnnotation;
import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.service.*;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import com.tsingtec.mini.vo.req.websocket.SignReqVO;
import com.tsingtec.mini.vo.resp.websocket.ChatInitDataRespVO;
import com.tsingtec.mini.vo.resp.websocket.ChatlogRespVO;
import com.tsingtec.mini.vo.resp.websocket.FriendRespVO;
import com.tsingtec.mini.vo.resp.websocket.MineRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author lj
 * @Date 2020/6/6 17:01
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "组织模块-用户管理")
public class ChatController {

    @Autowired
    private ChatlogService chatlogService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private ChatIdService chatIdService;

    @Autowired
    private MpUserService mpUserService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/chat/init")
    @ApiOperation(value = "获取聊天处理器主面板列表信息")
    @LogAnnotation(title = "聊天工具", action = "获取聊天处理器主面板列表信息")
    public DataResult<ChatInitDataRespVO> init(){
        DataResult<ChatInitDataRespVO> result = DataResult.success();
        Integer aid = HttpContextUtils.getAid();
        Doctor doctor = doctorService.findByAid(aid);
        if(null!=doctor){
            MineRespVO mineRespVO = friendService.getByUidAndMode(doctor.getMpUser(),"pc");
            List<FriendRespVO> friends = friendService.getByUid(mineRespVO.getId());

            ChatInitDataRespVO chatInitDataRespVO = new ChatInitDataRespVO();

            List<MineRespVO> chathistory = Lists.newArrayList();
            friends.forEach(friendRespVO -> {
                chathistory.addAll(friendRespVO.getList());
            });
            Map<String,MineRespVO> history = Maps.newHashMap();
            chathistory.forEach(s->{
                history.put("friend"+s.getId(),s);
            });
            chatInitDataRespVO.setHistory(history);
            List<Integer> chatids = chatIdService.getIdByIdsLike(mineRespVO.getId());

            //获取对话内容
            Map<String,List<ChatlogRespVO>> chatlog = chatlogService.findByChatidInLimit(mineRespVO.getId(),chatids);

            chatInitDataRespVO.setChatlog(chatlog);

            chatInitDataRespVO.setMine(mineRespVO);
            chatInitDataRespVO.setFriend(friends);
            result.setData(chatInitDataRespVO);
        }else{
            //返回非0的code
            result.setCode(1);
        }
        return result;
    }


    @GetMapping("/chat/history/{fid}")
    @ApiOperation(value = "获取聊天处理器主面板列表信息")
    @LogAnnotation(title = "聊天工具", action = "获取聊天记录")
    public DataResult<List<ChatlogRespVO>> history(@PathVariable("fid")Integer fid){
        DataResult<List<ChatlogRespVO>> result = DataResult.success();
        Integer aid = HttpContextUtils.getAid();
        Doctor doctor = doctorService.findByAid(aid);
        Integer chatId = chatIdService.getByToidAndFromId(doctor.getMpUser().getId(),fid);
        List<ChatlogRespVO> chatlogRespVOS = chatlogService.getChatLogByChatid(doctor.getMpUser().getId(),chatId);
        result.setData(chatlogRespVOS);
        return result;
    }

    @PutMapping("/chat/sign")
    @ApiOperation(value = "获取聊天处理器主面板列表信息")
    @LogAnnotation(title = "聊天工具", action = "获取聊天记录")
    public DataResult sign(@RequestBody SignReqVO signReqVO){
        System.out.println(signReqVO);
        friendService.update(signReqVO);
        return DataResult.success();
    }

}

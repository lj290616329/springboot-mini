package com.tsingtec.mini.controller.mini;

import com.google.common.collect.Lists;
import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.entity.mp.Information;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.entity.websocket.Chatlog;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.service.*;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import com.tsingtec.mini.vo.resp.websocket.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author lj
 * @Date 2020/6/8 15:32
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wxs/chat")
@Api(tags = "小程序模块--客服管理")
public class ChatMiniController {

    @Autowired
    private ChatlogService chatlogService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private ChatIdService chatIdService;

    @Autowired
    private MpUserService mpUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DoctorService doctorService;


    @Autowired
    private InformationService informationService;

    /**
     * 根据fid 获取聊天主页信息
     * @param fid
     * @return
     */
    @GetMapping("/init/{fid}/{mode}")
    @ApiOperation(value = "获取聊天处理器主面板列表信息")
    public DataResult<ChatInitDataRespVO> init(@PathVariable("fid")Integer fid,@PathVariable("mode")String mode){
        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);

        DataResult<ChatInitDataRespVO> result = DataResult.success();
        ToRespVO to = friendService.get(fid);
        if(null == to){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"对话对象不存在");
        }
        //初始化chatid
        MineRespVO mineRespVO = friendService.getByUidAndMode(mpUser,mode);

        //获取对话id
        Integer chatId = chatIdService.getByToidAndFromId(to.getId(),mineRespVO.getId());
        List<Integer> ids = Lists.newArrayList(chatId);

        ChatInitDataRespVO chatInitDataRespVO = new ChatInitDataRespVO();

        chatInitDataRespVO.setMine(mineRespVO);

        chatInitDataRespVO.setTo(to);

        //获取对话内容
        Map<String,List<ChatlogRespVO>> chatlog = chatlogService.findByChatids(mineRespVO.getId(),ids);

        chatInitDataRespVO.setChatlog(chatlog);

        result.setData(chatInitDataRespVO);
        //将未读的设置已读
        new Thread(){
            public void run(){
                List<Chatlog> chatLogs = chatlogService.findByFromidAndToidAndStatus(to.getId(),mineRespVO.getId(),false);
                chatLogs.forEach(c ->{
                    chatlogService.update(c);
                });
            }
        }.start();

        return result;
    }

    /**
     * 根据个人信息获取好友列表
     * @return
     */
    @GetMapping("/friends/{mode}")
    @ApiOperation(value = "获取聊天处理器主面板列表信息")
    public DataResult<List<FriendRespVO>> friends(@PathVariable("mode")String mode){
        DataResult<List<FriendRespVO>> result = DataResult.success();

        String token = HttpContextUtils.getToken();
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);
        //获取个人的id
        MineRespVO mineRespVO = friendService.getByUidAndMode(mpUser,mode.toLowerCase());
        //获取好友列表
        List<FriendRespVO> friends = friendService.getByUid(mineRespVO.getId());
        result.setData(friends);
        return result;
    }


    /**
     * 根据个人信息获取好友列表
     * @return
     */
    @GetMapping("/by/{did}")
    @ApiOperation(value = "根据医生id获得该医生的对话id")
    public DataResult<Integer> chatByDid(@PathVariable("did")Integer did){
        Doctor doctor = doctorService.findById(did);
        if(null==doctor){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"对话对象不存在");
        }
        DataResult<Integer> result = DataResult.success();
        MineRespVO friend =  friendService.getByUidAndMode(doctor.getMpUser(),"pc");
        if(null==friend){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"对话对象不存在");
        }
        result.setData(friend.getId());
        return result;
    }


    /**
     * 根据病人用户信息id 获取对话id
     * @return
     */
    @GetMapping("/by/information/{iid}")
    @ApiOperation(value = "根据用户id获得该病人的对话id")
    public DataResult<Integer> chatByIid(@PathVariable("iid")Integer iid){
        Information information = informationService.getOne(iid);
        if(null==information){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"对话对象不存在");
        }
        DataResult<Integer> result = DataResult.success();
        MineRespVO mineRespVO =  friendService.getByUidAndMode(information.getMpUser(),"mobile");
        if(null==mineRespVO){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"对话对象不存在");
        }
        result.setData(mineRespVO.getId());
        return result;
    }
}

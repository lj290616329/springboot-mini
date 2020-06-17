package com.tsingtec.mini.controller.mini;

import com.google.common.collect.Lists;
import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.service.ChatIdService;
import com.tsingtec.mini.service.ChatlogService;
import com.tsingtec.mini.service.FriendService;
import com.tsingtec.mini.service.MpUserService;
import com.tsingtec.mini.utils.DataResult;
import com.tsingtec.mini.utils.HttpContextUtils;
import com.tsingtec.mini.vo.resp.websocket.ChatInitDataRespVO;
import com.tsingtec.mini.vo.resp.websocket.ChatlogRespVO;
import com.tsingtec.mini.vo.resp.websocket.MineRespVO;
import com.tsingtec.mini.vo.resp.websocket.ToRespVO;
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
@Api(tags = "公众号模块--客服管理")
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

    @GetMapping("/init/{fid}")
    @ApiOperation(value = "获取聊天处理器主面板列表信息")
    public DataResult<ChatInitDataRespVO> init(@PathVariable("fid")Integer fid){
        String token = HttpContextUtils.getToken();
        System.out.println(token);
        String unionid = jwtUtil.getClaim(token,"unionid");
        MpUser mpUser = mpUserService.findByUnionId(unionid);

        DataResult<ChatInitDataRespVO> result = DataResult.success();

        ToRespVO to = friendService.get(fid);

        if(null == to){
            result.setMsg("该医生不存在");
            result.setCode(-1);
            return result;
        }

        //初始化chatid
        MineRespVO mineRespVO = friendService.getByUidAndType(mpUser,"mobile");

        //获取对话id
        Integer chatId = chatIdService.getByToidAndFromId(fid,mineRespVO.getId());
        List<Integer> ids = Lists.newArrayList(chatId);

        ChatInitDataRespVO chatInitDataRespVO = new ChatInitDataRespVO();

        chatInitDataRespVO.setMine(mineRespVO);

        chatInitDataRespVO.setTo(to);

        //获取对话内容
        Map<String,List<ChatlogRespVO>> history = chatlogService.findByChatids(mineRespVO.getId(),ids);

        chatInitDataRespVO.setHistory(history);

        result.setData(chatInitDataRespVO);

        return result;
    }

}

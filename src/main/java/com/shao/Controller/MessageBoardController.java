package com.shao.Controller;

import com.shao.Domain.Messageboard;
import com.shao.Domain.Result.ExceptionMsg;
import com.shao.Domain.Result.ResponseData;
import com.shao.Domain.User;
import com.shao.Domain.Userinfo;
import com.shao.Repository.MessageboardRepository;
import com.shao.Repository.UserInfoRepository;
import com.shao.Service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by shao on 2019/4/16 16:43.
 */
@RequestMapping(value = "messageBoard")
@RestController
public class MessageBoardController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    MessageboardRepository messageboardRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    @ApiOperation(value = "留言")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseData leaveMessage(String token,long courseId,String content){
        Optional<User> user = authenticationService.parseToken(token);
        if (user.equals(Optional.empty())){
            return new ResponseData(ExceptionMsg.WrongToken);
        }

        Messageboard messageboard = new Messageboard();
        messageboard.setUserId(user.get().getId());
        messageboard.setContent(content);
        messageboard.setCourseId(courseId);
        messageboardRepository.save(messageboard);

        return new ResponseData();
    }

    @ApiOperation(value = "获取留言列表")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseData getMessage(long courseId){
        List<Messageboard> messageboardList = messageboardRepository.findAllByCourseId(courseId);
        LinkedList<HashMap<String,String>> data = new LinkedList<>();
        for (Messageboard messageboard : messageboardList){
            HashMap<String,String> messageMap = new HashMap<>();
            Optional<Userinfo> userinfo = userInfoRepository.findById(messageboard.getUserId());
            userinfo.ifPresent(value -> messageMap.put("user", value.getNickname()));
            messageMap.put("content", messageboard.getContent());
            messageMap.put("createtime", String.valueOf(messageboard.getCreatetime()));
            data.add(messageMap);
        }
        return new ResponseData(data);
    }
}

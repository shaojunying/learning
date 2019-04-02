package com.shao.Controller;

import com.shao.Domain.User;
import com.shao.Service.UserService;
import com.shao.Service.UserServiceImpl;
import com.shao.util.CodeInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shao on 2019/4/2 15:40.
 */
@RestController
@RequestMapping(value = "users")
public class UserController {

    UserService userService = new UserServiceImpl();

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getUsers(@ModelAttribute User user){
        return "...";
    }


    @ApiOperation(value = "创建用户",notes = "根据User对象创建用户")
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public CodeInfo createUsers(@ModelAttribute User user){
        return userService.createUsers(user);
    }

    @ApiOperation(value = "验证用户",notes = "根据User对象验证用户")
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public CodeInfo verifyUsers(@ModelAttribute User user){
        return userService.verifyUsers(user);
    }


}

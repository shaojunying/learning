package com.shao.Controller;

import com.shao.DAO.UserRepository;
import com.shao.Entity.User;
import com.shao.util.CodeInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getUsers(@ModelAttribute User user){
//        userRepository.findAll();
        return "...";
    }


    @ApiOperation(value = "创建用户",notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user",value = "用户详细实体user",required = true,dataType = "User")
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public CodeInfo createUsers(@ModelAttribute User user){
        if (userRepository.findByUsername(user.getUsername()) != null){
            return new CodeInfo(401,"用户已存在");
        }
        userRepository.save(user);
        return new CodeInfo(200);
    }

    @ApiOperation(value = "验证用户",notes = "根据User对象验证用户")
    @ApiImplicitParam(name = "user",value = "用户详细实体user",required = true,dataType = "User")
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public CodeInfo verifyUsers(@ModelAttribute User user){
        User user1 = userRepository.findByUsername(user.getUsername());
        if (user1==null){
            return new CodeInfo(401,"用户不存在");
        }
        if (user.getPassword().equals(user1.getPassword())){
            return new CodeInfo(200);
        }
        return new CodeInfo(401,"用户密码输入有误");
    }


}

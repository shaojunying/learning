package com.shao.Controller;

import com.shao.Domain.Result.ExceptionMsg;
import com.shao.Domain.Result.ResponseData;
import com.shao.Domain.User;
import com.shao.Repository.UserRepository;
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
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getUsers(@ModelAttribute User user){
        return "...";
    }

    @ApiOperation(value = "创建用户",notes = "根据User对象创建用户")
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public ResponseData create(@ModelAttribute User user){
        try {
            User createUser = userRepository.findByUsername(user.getUsername());
            if (createUser!=null){
                return new ResponseData(ExceptionMsg.ExistingUsername);
            }
            userRepository.save(user);
            return new ResponseData();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }
    @ApiOperation(value = "验证用户",notes = "根据User对象验证用户")
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseData login(@ModelAttribute User user){
        try {
            User loginUser = userRepository.findByUsername(user.getUsername());
            if (loginUser == null){
                return new ResponseData(ExceptionMsg.LoginNameNotExists);
            }
            if (!loginUser.getPassword().equals(user.getPassword())){
                return new ResponseData(ExceptionMsg.WrongPassword);
            }
            return new ResponseData();
        }
        catch (Exception e){
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }
}

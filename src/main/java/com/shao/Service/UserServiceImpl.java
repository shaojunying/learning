package com.shao.Service;

import com.shao.Domain.User;
import com.shao.Repository.UserRepository;
import com.shao.util.CodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by shao on 2019/4/2 23:18.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
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

    @Override
    public CodeInfo createUsers(@ModelAttribute User user){
        if (userRepository.findByUsername(user.getUsername()) != null){
            return new CodeInfo(401,"用户已存在");
        }
        userRepository.save(user);
        return new CodeInfo(200);
    }

}

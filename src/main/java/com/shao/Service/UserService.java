package com.shao.Service;

import com.shao.Domain.User;
import com.shao.util.CodeInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by shao on 2019/4/2 23:19.
 */
public interface UserService{

    /*
    * 验证用用户信息是否匹配
    * */
    CodeInfo verifyUsers(@ModelAttribute User user);

    /*
    * 根据User信息创建新用户
    * */
    CodeInfo createUsers(@ModelAttribute User user);
}

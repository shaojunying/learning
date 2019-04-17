package com.shao.Controller;

import com.shao.Domain.Course;
import com.shao.Domain.Result.ExceptionMsg;
import com.shao.Domain.Result.ResponseData;
import com.shao.Domain.User;
import com.shao.Domain.UserHasCourse;
import com.shao.Domain.Userinfo;
import com.shao.Repository.CourseRepository;
import com.shao.Repository.UserHasCourseRepository;
import com.shao.Repository.UserInfoRepository;
import com.shao.Repository.UserRepository;
import com.shao.Service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by shao on 2019/4/2 15:40.
 */
@RestController
@RequestMapping(value = "users")
public class UserController{
    private final UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserHasCourseRepository userHasCourseRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String getUsers(@ModelAttribute User user){
        return "...";
    }

    @ApiOperation(value = "注册",notes = "根据User对象创建用户")
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public ResponseData create(@ModelAttribute User user){
        try {
            User createUser = userRepository.findByUid(user.getUid());
            if (createUser!=null){
                return new ResponseData(ExceptionMsg.ExistingUsername);
            }
            /*将用户的学号与密码进行保存*/
            userRepository.save(user);

            /*
            * 为用户创建一行默认信息
            * */
            user = userRepository.findByUid(user.getUid());
            Userinfo userinfo = new Userinfo(user.getId());
            userInfoRepository.save(userinfo);
            return new ResponseData();
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }


    @ApiOperation(value = "登陆",notes = "根据User对象验证用户")
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseData login(@ModelAttribute User user){
        try {
            User loginUser = userRepository.findByUid(user.getUid());
            if (loginUser == null){
                return new ResponseData(ExceptionMsg.LoginNameNotExists);
            }
            if (!loginUser.getPassword().equals(user.getPassword())){
                return new ResponseData(ExceptionMsg.WrongPassword);
            }
            String token = authenticationService.getToken(loginUser);
            HashMap<String,String> result = new HashMap<>();
            result.put("uid", loginUser.getUid());
            result.put("token", token);
            return new ResponseData(ExceptionMsg.SUCCESS,result);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }

    @ApiOperation(value = "个人信息",notes = "根据token返回用户个人信息")
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public ResponseData getInfo(String token){
        try {
            Optional<User> user = authenticationService.parseToken(token);
            if (user.equals(Optional.empty())){
                return new ResponseData(ExceptionMsg.WrongToken);
            }

            Userinfo userinfo = userInfoRepository.findByUserId(user.get().getId());

            HashMap<String,String> result = new HashMap<>();
            result.put("nickname",userinfo.getNickname());
            result.put("email",userinfo.getEmail());
            result.put("uid", user.get().getUid());
            result.put("name", userinfo.getName());
            result.put("phone",userinfo.getPhone());
            result.put("info", userinfo.getInfo());
            return new ResponseData(result);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }


    @ApiOperation(value = "个人信息",notes = "根据token修改用户个人信息")
    @RequestMapping(value = "info",method = RequestMethod.PUT)
    public ResponseData changeInfo(String token,Userinfo loginUserinfo){
        try {
            Optional<User> user = authenticationService.parseToken(token);
            if (user.equals(Optional.empty())){
                return new ResponseData(ExceptionMsg.WrongToken);
            }
            Userinfo userinfo = userInfoRepository.findByUserId(user.get().getId());
            userinfo.setNickname(loginUserinfo.getNickname());
            userinfo.setEmail(loginUserinfo.getEmail());
            userinfo.setName(loginUserinfo.getName());
            userinfo.setPhone(loginUserinfo.getPhone());
            userinfo.setInfo(loginUserinfo.getInfo());
            userInfoRepository.save(userinfo);
            return new ResponseData();
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }

    @ApiOperation(value = "获取某位老师的讲述的所有课程")
    @RequestMapping(value = "allCourse",method =RequestMethod.GET)
    public ResponseData getAllCourseByTeacherId(String token){
        Optional<User> user = authenticationService.parseToken(token);
        if (user.equals(Optional.empty())) {
            return new ResponseData(ExceptionMsg.WrongToken);
        }
        long userId = user.get().getId();
        List<Course> courseList = courseRepository.findAllByTeacherId(userId);
        List<HashMap<String,String>> courseMapList = new LinkedList<>();
        for (Course course : courseList){
            HashMap<String,String> courseMap = new HashMap<>();
            courseMap.put("name", course.getName());
            courseMap.put("id", String.valueOf(course.getId()));
            courseMapList.add(courseMap);
        }
        return new ResponseData(courseMapList);
    }

    @Modifying
    @Transactional
    @ApiOperation(value = "删除某位学生的某门课程")
    @RequestMapping(value = "{courseId}",method = RequestMethod.DELETE)
    public ResponseData deleteUserFromCourse(@PathVariable long courseId,String uid){
        User user= userRepository.findByUid(uid);
        userHasCourseRepository.deleteByUserIdAndCourseId(user.getId(),courseId);
        return new ResponseData();
    }

}

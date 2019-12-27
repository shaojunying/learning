package com.shao.Controller;

import com.shao.Domain.*;
import com.shao.Domain.Result.ExceptionMsg;
import com.shao.Domain.Result.ResponseData;
import com.shao.Repository.*;
import com.shao.Service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by shao on 2019/4/13 17:19.
 */
@RestController
@RequestMapping(value = "course")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserHasCourseRepository userHasCourseRepository;


    @ApiOperation(value = "获取所有课程的简略信息")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseData getCourses(){
        List<Course> courseList = courseRepository.findAll();
        LinkedList<HashMap<String,String>> result = new LinkedList<>();
        for (Course course : courseList){
            HashMap<String,String> courseMap = new HashMap<>();
            courseMap.put("name", course.getName());
            courseMap.put("description", course.getDescription());
            courseMap.put("id", String.valueOf(course.getId()));
            result.add(courseMap);
        }
        return new ResponseData(result);
    }

    @ApiOperation(value = "获取指定课程的详细信息")
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public ResponseData getCourse(@PathVariable long id){
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent()){
            return new ResponseData(ExceptionMsg.NotExistCourse);
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("name",course.get().getName());
        map.put("description", course.get().getDescription());
        map.put("overview",course.get().getOverview());
        map.put("target",course.get().getTarget());
        map.put("outline",course.get().getOutline());
        map.put("length", String.valueOf(course.get().getLength()));
        if (course.get().getTeacherId()!=null){
            Userinfo teacherInfo = userInfoRepository.findByUserId(course.get().getTeacherId());
            map.put("teacher", teacherInfo.getName());
        }
        return new ResponseData(map);
    }

    @ApiOperation(value = "获取指定课程的章节信息")
    @RequestMapping(value = "{id}/chapter",method = RequestMethod.GET)
    public ResponseData getCourseChapter(@PathVariable long id){
        List<Chapter> chapterList = chapterRepository.findAllByCourseId(id);
        HashMap<String,HashMap<String,String>> chapters = new HashMap<>();
        for (Chapter chapter : chapterList){
            List<Section> sectionList = sectionRepository.findAllByChapterId(chapter.getId());
            HashMap<String,String> sections = new HashMap<>();
            for (Section section : sectionList){
                sections.put(section.getName(),section.getVideoUrl());
            }
            chapters.put(chapter.getName(), sections);
        }
        return new ResponseData(chapters);
    }

    @ApiOperation(value = "学生进行选课")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseData choiceCourse(String token,long courseId){
        Optional<User> user = authenticationService.parseToken(token);
        if (user.equals(Optional.empty())){
            return new ResponseData(ExceptionMsg.WrongToken);
        }
        Optional<Course> course = courseRepository.findById(courseId);
        if (user.equals(Optional.empty())){
            return new ResponseData(ExceptionMsg.WrongCourseId);
        }
        UserHasCourse userHasCourse = new UserHasCourse(user.get().getId(), courseId);
        userHasCourseRepository.save(userHasCourse);
        return new ResponseData();
    }

    @ApiOperation(value = "获取选择该课程的所有学生")
    @RequestMapping(value = "allStudents",method = RequestMethod.GET)
    public ResponseData getAllStudentsByCourse(long courseId){
        List<UserHasCourse> userHasCourseList =  userHasCourseRepository.findAllByCourseId(courseId);

        LinkedList<Map<String,String>> data = new LinkedList<>();
        for (UserHasCourse userHasCourse :userHasCourseList){
            Userinfo userinfo = userInfoRepository.findByUserId(userHasCourse.getUserId());
            HashMap<String,String> userMap = new HashMap<>();
            userMap.put("name", userinfo.getName());
            Optional<User> user = userRepository.findById(userHasCourse.getUserId());
            user.ifPresent(value -> userMap.put("uid", value.getUid()));
            data.add(userMap);
        }
        return new ResponseData(data);
    }

}

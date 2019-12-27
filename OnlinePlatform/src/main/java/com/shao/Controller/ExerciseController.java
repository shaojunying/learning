package com.shao.Controller;

import com.shao.Domain.Chapter;
import com.shao.Domain.Exerises;
import com.shao.Domain.Result.ResponseData;
import com.shao.Repository.ChapterRepository;
import com.shao.Repository.ExerciseRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shao on 2019/4/17 15:46.
 */
@RestController
@RequestMapping(value = "exercise")
public class ExerciseController {
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    ChapterRepository chapterRepository;


    @ApiOperation(value = "获取某课程所有章节对应的所有练习")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseData getAllExerciseByCourseId(long courseId){
        List<Chapter> chapterList = chapterRepository.findAllByCourseId(courseId);
        HashMap<String, List<HashMap<String,String>>> data = new HashMap<>();
        for (Chapter chapter : chapterList){
            List<Exerises> exerisesList = exerciseRepository.findAllByChapterId(chapter.getId());
            LinkedList<HashMap<String,String>> exerciseMapList = new LinkedList<>();
            for (Exerises exerise : exerisesList){
                HashMap<String,String> exerciseMap = new HashMap<>();
                exerciseMap.put("content", exerise.getContent());
                exerciseMap.put("answer", exerise.getAnswer());
                exerciseMapList.add(exerciseMap);
            }
            data.put(chapter.getName(), exerciseMapList);
        }
        return new ResponseData(data);
    }

}

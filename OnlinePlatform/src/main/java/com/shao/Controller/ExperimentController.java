package com.shao.Controller;

import com.shao.Domain.Experiment;
import com.shao.Domain.Result.ResponseData;
import com.shao.Repository.ExperimentRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shao on 2019/4/17 15:22.
 */
@RestController
@RequestMapping(value = "experiment")
public class ExperimentController {
    @Autowired
    ExperimentRepository experimentRepository;

    @ApiOperation(value = "获取某课程对应的所有实验")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseData getAllExperimentByCourseId(long courseId){
        LinkedList<HashMap<String,String>> data = new LinkedList<>();
        List<Experiment> experimentList = experimentRepository.findAllByCourseId(courseId);
        for (Experiment experiment : experimentList){
            HashMap<String,String> experimentMap = new HashMap<>();
            experimentMap.put("name",experiment.getName());
            experimentMap.put("url", experiment.getInstructionUrl());
            data.add(experimentMap);
        }
        return new ResponseData(data);
    }

}

package com.shao.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shao on 2019/4/2 16:17.
 */
@RestController
public class MainController {

    @GetMapping("/")
    public String index(){
        return "hello";
    }

}

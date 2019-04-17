package com.shao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.WebApplicationInitializer;

/**
 * Created by shao on 2019/4/2 14:55.
 */
@SpringBootApplication
@EnableJpaAuditing
public class Test_1Application extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(Test_1Application.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(Test_1Application.class, args);
    }
}

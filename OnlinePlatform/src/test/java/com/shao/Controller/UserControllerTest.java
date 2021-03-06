package com.shao.Controller;

import com.shao.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by shao on 2019/4/2 16:42.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void postUsers() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/users")
//        .pa)

        // 测试UserController
//        RequestBuilder request = null;
//        request = get("/users/");
//        mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().string(equalTo("[]")));


    }
}

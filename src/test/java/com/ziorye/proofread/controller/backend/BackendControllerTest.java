package com.ziorye.proofread.controller.backend;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BackendControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    void dashboard() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/backend/dashboard"))
                .andExpect(MockMvcResultMatchers.view().name("backend/dashboard"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("后台首页")))
        ;
    }

    @Test
    void empty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/backend/empty"))
                .andExpect(MockMvcResultMatchers.view().name("backend/empty"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("空白页")))
        ;
    }
}
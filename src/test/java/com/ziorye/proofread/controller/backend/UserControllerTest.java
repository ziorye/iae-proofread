package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.bean.backend.BackendMenus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"admin"})
class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    BackendMenus backendMenus;

    @Test
    void users() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/backend/users"))
                .andExpect(MockMvcResultMatchers.view().name("backend/user/index"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("用户管理")))
        ;
    }
}
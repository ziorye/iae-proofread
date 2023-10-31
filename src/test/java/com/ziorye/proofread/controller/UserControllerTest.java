package com.ziorye.proofread.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    void login() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("登录")))
                //.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Please sign in")))
        ;
    }

    @Test
    void loginCheckedRememberMe() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "user")
                        .param("password", "password")
                        .param("remember-me", "on")
                )
                .andExpect(MockMvcResultMatchers.cookie().exists("remember-me"))
        ;
    }
}
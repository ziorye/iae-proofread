package com.ziorye.proofread.controller;

import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

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

    @Test
    void loginByNameExpectFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "user")
                        .param("password", "password")
                )
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"))
        ;
    }

    @Test
    void loginByEmail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "user@example.com")
                        .param("password", "password")
                )
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/dashboard"))
        ;
    }

    @Test
    void userDashboardWithoutLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/dashboard"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        ;
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "jpaUserDetailsService", value = "user@example.com")
    void userDashboardWithLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/dashboard"))
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("user@example.com")))
        ;
    }

    @Test
    void userRegisterWithExistingEmail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "new-name")
                        .param("email", "admin@example.com")
                        .param("password", "secret")
                )
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("user", "email", "exist"))
        ;
    }

    @Test
    void userRegisterAutoLogin() throws Exception {
        String randomStr = UUID.randomUUID().toString().substring(0, 6);
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", randomStr)
                        .param("email", randomStr + "@example.com")
                        .param("password", "secret")
                )
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
        ;
    }

    @Test
    void getPasswordRestPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/password-reset"))
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("发送重置密码链接")))
        ;

    }

    @Test
    void postPasswordRestWithNonExistentEmail() throws Exception {
        String randomStr = UUID.randomUUID().toString().substring(0, 6);
        mvc.perform(MockMvcRequestBuilders.post("/user/password-reset")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", randomStr + "@example.com")
                )
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("passwordResetEmail", "email", "non-existent"))
        ;
    }

    @Test
    void postPasswordRestWithExistEmail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/password-reset")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "admin@example.com")
                )
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "密码重置邮件已发送，请注意查收"))
        ;
    }
}
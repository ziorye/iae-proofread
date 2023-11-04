package com.ziorye.proofread.controller.advice;

import com.ziorye.proofread.bean.backend.BackendMenus;
import com.ziorye.proofread.bean.backend.Menu;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("requestURI")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @Autowired
    BackendMenus backendMenus;
    @ModelAttribute("menus")
    List<Menu> getBackendMenus() {
        return backendMenus.getMenus();
    }
}

package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.bean.backend.BackendMenus;
import com.ziorye.proofread.bean.backend.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/backend")
public class BackendController {
    @Autowired
    BackendMenus backendMenus;
    @ModelAttribute("menus")
    List<Menu> getBackendMenus() {
        return backendMenus.getMenus();
    }

    @GetMapping("dashboard")
    String dashboard() {
        return "backend/dashboard";
    }

    @GetMapping("empty")
    String empty() {
        return "backend/empty";
    }
}

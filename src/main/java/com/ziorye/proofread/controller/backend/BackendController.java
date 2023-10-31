package com.ziorye.proofread.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backend")
public class BackendController {
    @GetMapping("dashboard")
    String dashboard() {
        return "backend/dashboard";
    }

    @GetMapping("empty")
    String empty() {
        return "backend/empty";
    }
}

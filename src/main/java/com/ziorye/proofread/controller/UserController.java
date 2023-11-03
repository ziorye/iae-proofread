package com.ziorye.proofread.controller;

import com.ziorye.proofread.dto.UserDto;
import com.ziorye.proofread.entity.User;
import com.ziorye.proofread.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("dashboard")
    @PreAuthorize("isAuthenticated()")
    String dashboard() {
        return "user/dashboard";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user/register";
    }

    @PostMapping("register")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model,
                               HttpServletRequest request){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", "exist", "该邮箱已被注册");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/user/register";
        }

        userService.saveUser(userDto);

        // === === ===
        try {
            request.login(userDto.getEmail(), userDto.getPassword());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        // === === ===

        return "redirect:/user/dashboard";
    }
}

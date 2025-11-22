package org.unpar.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.service.PenggunaService;

@Controller
@RequestMapping("login")
public class AuthController {
    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/")
    public String login() {
        return "login/index";
    }

    @GetMapping("/change_password")
    public String changePassword() {
        return "login/change_password";
    }

    @GetMapping("/forgot_password")
    public String forgotPassword() {
        return "login/forgot_password";
    }

    @PostMapping("/")
    public String checkLogin(HttpServletRequest request) {
        return "redirect:/login";
    }

}

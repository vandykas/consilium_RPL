package org.unpar.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.unpar.project.service.PenggunaService;

@Controller
public class AuthController {
    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/login")
    public String login() {
        return "Login/login";
    }

    @GetMapping("/change_password")
    public String changePassword() {
        return "Login/change_password";
    }

    @GetMapping("/forgot_password")
    public String forgotPassword() {
        return "Login/forgot_password";
    }

    @PostMapping("/login")
    public String checkLogin(HttpServletRequest request) {
        return "redirect:/login";
    }
}

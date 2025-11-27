package org.unpar.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.PenggunaService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/login")
    public String viewLogin() {
        return "login/login";
    }

    @GetMapping("/change_password")
    public String viewChangePassword() {
        return "login/change_password";
    }

    @GetMapping("/forgot_password")
    public String viewForgotPassword() {
        return "login/forgot_password";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request,
                             HttpSession session) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        return penggunaService.login(email, password)
                .map(pengguna -> handleSuccessfulLogin(pengguna, session))
                .orElse("redirect:/login");
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    private String handleSuccessfulLogin(Pengguna pengguna, HttpSession session) {
        String role = penggunaService.getRoleFromId(pengguna.getIdPengguna());

        session.setAttribute("name", pengguna.getNama());
        session.setAttribute("id", pengguna.getIdPengguna());
        session.setAttribute("role", role);

        return "redirect:/beranda/" + role;
    }
}

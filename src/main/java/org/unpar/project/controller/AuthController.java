package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.unpar.project.dto.ChangePasswordRequest;
import org.unpar.project.dto.LoginRequest;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.PenggunaService;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/login")
    public String viewLogin(LoginRequest loginRequest) {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginRequest loginRequest,
                        BindingResult bindingResult,
                        HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login/login";
        }

        Optional<Pengguna> penggunaOpt = penggunaService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (penggunaOpt.isEmpty()) {
            return "login/login";
        }

        Pengguna pengguna = penggunaOpt.get();
        session.setAttribute("pengguna", pengguna);
        if (!penggunaService.isEverLogin(pengguna.getIdPengguna())) {
            return "redirect:/change_password";
        }

        return "redirect:/beranda/" + pengguna.getRole();
    }

    @GetMapping("/change_password")
    public String viewChangePassword(ChangePasswordRequest changePasswordRequest) {
        return "login/change_password";
    }

    @PostMapping("/change_password")
    public String changePassword(@Valid ChangePasswordRequest changePasswordRequest,
                                 BindingResult bindingResult,
                                 HttpSession session) {
        if (!isPasswordMatching(changePasswordRequest.getPassword(), changePasswordRequest.getConfirmPassword())) {
            bindingResult.rejectValue(
                    "confirmPassword",
                    "PasswordMismatch",
                    "Password tidak sama"
            );
        }

        if (bindingResult.hasErrors()) {
            return "login/change_password";
        }

        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        penggunaService.updatePassAndChangeStatus(pengguna.getIdPengguna(), changePasswordRequest.getPassword());
        return "redirect:/beranda/" + pengguna.getRole();
    }

    @GetMapping("/forgot_password")
    public String viewForgotPassword() {
        return "login/forgot_password";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    private boolean isPasswordMatching(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}

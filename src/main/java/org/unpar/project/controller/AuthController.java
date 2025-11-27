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
    public String login() {
        return "login/login";
    }

    @GetMapping("/change_password")
    public String changePassword() {
        return "login/change_password";
    }

    @GetMapping("/forgot_password")
    public String forgotPassword() {
        return "login/forgot_password";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request,
                             HttpSession session) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<Pengguna> pengguna = penggunaService.login(email, password);

        if (pengguna.isPresent()) {
            Map<Character, String> toRole = new HashMap<>();
            toRole.put('D', "dosen");
            toRole.put('M', "mahasiswa");
            toRole.put('A', "admin");

            Pengguna p = pengguna.get();
            String role = toRole.get(p.getIdPengguna().charAt(0));

            session.setAttribute("name", p.getNama());
            session.setAttribute("id", p.getIdPengguna());
            session.setAttribute("role", role);

            return "redirect:/beranda/" + role;
        }
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

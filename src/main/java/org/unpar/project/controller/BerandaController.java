package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.service.PenggunaService;

@Controller
@RequestMapping("beranda")
public class BerandaController {

    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/mahasiswa")
    public String viewBerandaMahasiswa(Model model,
                                   HttpSession session) {
        addCommonAttributes(model, "mahasiswa");
        model.addAttribute("name", session.getAttribute("name"));
        return "beranda/mahasiswa";
    }

    @GetMapping("/dosen")
    public String viewBerandaDosen(Model model,
                               HttpSession session) {
        addCommonAttributes(model, "dosen");
        model.addAttribute("name", session.getAttribute("name"));
        return "beranda/dosen";
    }
    @GetMapping("/admin")
    public String viewBerandaAdmin(Model model) {
        addCommonAttributes(model, "admin");
        return "beranda/admin";
    }

    private void addCommonAttributes(Model model, String role) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", role);
    }
}

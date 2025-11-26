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
    public String berandaMahasiswa(Model model,
                                   HttpSession session) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", "mahasiswa");
        model.addAttribute("name", session.getAttribute("name"));
        return "beranda/mahasiswa";
    }

    @GetMapping("/dosen")
    public String berandaDosen(Model model,
                               HttpSession session) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", "dosen");
        model.addAttribute("name", session.getAttribute("name"));
        return "beranda/dosen";
    }
    @GetMapping("/admin")
    public String berandaAdmin(Model model) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", "admin");
        return "beranda/admin";
    }
}

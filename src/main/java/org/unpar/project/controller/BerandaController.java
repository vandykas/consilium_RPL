package org.unpar.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("beranda")
public class BerandaController {
    @GetMapping("/mahasiswa")
    public String berandaMahasiswa(Model model) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", "mahasiswa");
        return "beranda/mahasiswa";
    }

    @GetMapping("/dosen")
    public String berandaDosen(Model model) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", "dosen");
        return "beranda/dosen";
    }
    @GetMapping("/admin")
    public String berandaAdmin(Model model) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", "admin");
        return "beranda/admin";
    }
}

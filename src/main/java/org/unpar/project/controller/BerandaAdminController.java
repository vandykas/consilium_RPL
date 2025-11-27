package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.MahasiswaService;
import org.unpar.project.service.PenggunaService;
import org.unpar.project.service.TopikService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class BerandaAdminController {
    @GetMapping("/mahasiswa")
    public String viewDaftarMahasiswa(Model model) {
        model.addAttribute("currentPage", "adminMahasiswa");
        model.addAttribute("currentRole", "admin");
        return "beranda/adminMahasiswa";
    }

    @GetMapping("/dosen")
    public String viewDaftarDosen(Model model) {
        model.addAttribute("currentPage", "adminDosen");
        model.addAttribute("currentRole", "admin");
        return "beranda/adminDosen";
    }

    private void addCommonAttributes(Model model, String role) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", role);
    }
}

package org.unpar.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("jadwal")
public class JadwalBimbinganController {
    @GetMapping("/mahasiswa")
    public String jadwalMahasiswa(Model model) {
        model.addAttribute("currentPage", "jadwal");
        model.addAttribute("currentRole", "mahasiswa");
        return "jadwal/mahasiswa";
    }
}

package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("jadwal")
public class JadwalBimbinganController {
    @GetMapping("/mahasiswa")
    public String jadwalMahasiswa(Model model,
                                  HttpSession session) {
        model.addAttribute("currentPage", "jadwal");
        model.addAttribute("currentRole", "mahasiswa");
        model.addAttribute("name",  session.getAttribute("name"));
        return "jadwal/mahasiswa";
    }

    @GetMapping("/dosen")
    public String jadwalDosen(Model model) {
        model.addAttribute("currentPage", "jadwal");
        model.addAttribute("currentRole", "dosen");
        return "jadwal/dosen";
    }
}

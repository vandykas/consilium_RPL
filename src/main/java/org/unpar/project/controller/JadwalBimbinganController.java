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
    public String viewJadwalMahasiswa(Model model,
                                      HttpSession session) {
        addCommonAttributes(model, "mahasiswa");
        model.addAttribute("name",  session.getAttribute("name"));
        return "jadwal/mahasiswa";
    }

    @GetMapping("/dosen")
    public String viewJadwalDosen(Model model,
                                  HttpSession session) {
        addCommonAttributes(model, "dosen");
        model.addAttribute("name",  session.getAttribute("name"));
        return "jadwal/dosen";
    }

    private void addCommonAttributes(Model model, String role) {
        model.addAttribute("currentPage", "jadwal");
        model.addAttribute("currentRole", role);
    }
}

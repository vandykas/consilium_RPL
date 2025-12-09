package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Pengguna;

@Controller
@RequestMapping("/pengumuman")
public class PengumumanController {

    @GetMapping("/mahasiswa")
    public String viewPengumumanMahasiswa(Model model,
                                          HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, pengguna);
        model.addAttribute("name", session.getAttribute("name"));
        return "pengumuman/pengumuman_bimbingan";
    }

    @GetMapping("/dosen")
    public String viewPengumumanDosen(Model model,
                                      HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, pengguna);
        model.addAttribute("name", session.getAttribute("name"));
        return "pengumuman/pengumuman";
    }

    private void addCommonAttributes(Model model, Pengguna pengguna) {
        model.addAttribute("currentPage", "pengumuman");
        model.addAttribute("pengguna", pengguna);
    }
}

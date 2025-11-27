package org.unpar.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("notifikasi")
public class NotifikasiController {
    @GetMapping("/mahasiswa")
    public String viewNotifikasiMahasiswa(Model model) {
        addCommonAttributes(model, "mahasiswa");
        return "notifikasi/notifikasi";
    }

    @GetMapping("/dosen")
    public String viewNotifikasiDosen(Model model) {
        addCommonAttributes(model, "dosen");
        return "notifikasi/notifikasi";
    }

    private void addCommonAttributes(Model model, String role) {
        model.addAttribute("currentPage", "notifikasi");
        model.addAttribute("currentRole", role);
    }
}

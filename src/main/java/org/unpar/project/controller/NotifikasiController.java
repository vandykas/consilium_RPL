package org.unpar.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("notifikasi")
public class NotifikasiController {
    @GetMapping("/mahasiswa")
    public String notifikasiMahasiswa(Model model) {
        model.addAttribute("currentPage","notifikasi");
        model.addAttribute("currentRole","mahasiswa");
        return "notifikasi/notifikasi";
    }

    @GetMapping("/dosen")
    public String notifikasiDosen(Model model) {
        model.addAttribute("currentPage","notifikasi");
        model.addAttribute("currentRole","dosen");
        return "notifikasi/notifikasi";
    }
}

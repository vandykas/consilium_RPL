package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Pengguna;
import org.unpar.project.repository.NotifikasiRepository;
import org.unpar.project.service.NotifikasiService;

@Controller
@RequestMapping("notifikasi")
public class NotifikasiController {
    @Autowired
    private NotifikasiService notifikasiService;

    @GetMapping("/mahasiswa")
    public String viewNotifikasiMahasiswa(Model model,
                                          HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, "mahasiswa", pengguna.getIdPengguna());
        return "notifikasi/notifikasi";
    }

    @GetMapping("/dosen")
    public String viewNotifikasiDosen(Model model,
                                      HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, "dosen", pengguna.getIdPengguna());
        return "notifikasi/notifikasi";
    }

    private void addCommonAttributes(Model model, String role, String id) {
        model.addAttribute("currentPage", "notifikasi");
        model.addAttribute("currentRole", role);
        model.addAttribute("notifications", notifikasiService.getAllNotifikasiById(id));
    }
}

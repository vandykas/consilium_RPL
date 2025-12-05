package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.MahasiswaService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("jadwal")
public class JadwalBimbinganController {
    private final LocalDate awalKuliah = LocalDate.of(2025, 9, 1);

    @Autowired
    private MahasiswaService mahasiswaService;

    @GetMapping("/mahasiswa")
    public String viewJadwalMahasiswa(Model model,
                                      HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");

        addCommonAttributes(model, pengguna);
        checkBeforeOrAfterUTS(model, LocalDate.now(), pengguna.getIdPengguna());
        return "jadwal/mahasiswa";
    }

    @GetMapping("/dosen")
    public String viewJadwalDosen(Model model,
                                  HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");

        addCommonAttributes(model, pengguna);
        return "jadwal/dosen";
    }

    private void addCommonAttributes(Model model, Pengguna pengguna) {
        model.addAttribute("currentPage", "jadwal");
        model.addAttribute("pengguna", pengguna);
    }

    private void checkBeforeOrAfterUTS(Model model, LocalDate now, String id) {
        long week = ChronoUnit.WEEKS.between(awalKuliah, now) + 1;
        if (week <= 7) {
            model.addAttribute("isSebelumUts", true);
            model.addAttribute("countBimbingan", mahasiswaService.getCounterBimbinganBeforeUTS(id));
        }
        else {
            model.addAttribute("isSebelumUts", false);
            model.addAttribute("countBimbingan", mahasiswaService.getCounterBimbinganAfterUTS(id));
        }
    }
}

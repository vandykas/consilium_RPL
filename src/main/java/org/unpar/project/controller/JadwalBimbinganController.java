package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.unpar.project.dto.BimbinganKalender;
import org.unpar.project.model.Bimbingan;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.BimbinganService;
import org.unpar.project.service.MahasiswaService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("jadwal")
public class JadwalBimbinganController {
    private final LocalDate awalKuliah = LocalDate.of(2025, 9, 1);

    @Autowired
    private MahasiswaService mahasiswaService;
    @Autowired
    private BimbinganService bimbinganService;

    @GetMapping("/mahasiswa")
    public String viewJadwalMahasiswa(@RequestParam(defaultValue = "0") int weekOffset,
                                      Model model,
                                      HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");

        model.addAttribute("weekOffset", weekOffset);
        addCommonAttributes(model, pengguna);
        addDaysLabel(model, weekOffset);
        addUpcomingBimbingan(model, weekOffset, pengguna.getIdPengguna());
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

    private void addDaysLabel(Model model, int weekOffset) {
        List<LocalDate> tanggalList = bimbinganService.getDaysLabel(weekOffset);
        model.addAttribute("tanggalMingguIni", tanggalList);
    }

    private void addUpcomingBimbingan(Model model, int weekOffset, String idPengguna) {
        List<BimbinganKalender> bimbinganList = bimbinganService.findAllBimbingan(idPengguna, weekOffset);
        System.out.println("Isi bimbinganList: " + bimbinganList);
        System.out.println("Jumlah: " + bimbinganList.size());
        System.out.println("Kosong? " + bimbinganList.isEmpty());
        model.addAttribute("bimbinganList", bimbinganList);
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

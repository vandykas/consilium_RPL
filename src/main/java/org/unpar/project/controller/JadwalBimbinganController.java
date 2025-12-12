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
import org.unpar.project.service.KuliahService;
import org.unpar.project.service.MahasiswaService;
import org.unpar.project.service.PenggunaService;

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
    @Autowired
    private KuliahService kuliahService;

    @GetMapping("/mahasiswa")
    public String viewJadwalMahasiswa(@RequestParam(defaultValue = "0") int weekOffset,
                                      Model model,
                                      HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        String idPengguna = pengguna.getIdPengguna();

        addCommonAttributes(model, pengguna, weekOffset);
        addDaysLabel(model, weekOffset);
        addBimbinganToCalendarMahasiswa(model, weekOffset, idPengguna);
        addBlockedJadwal(model, idPengguna);
        checkBeforeOrAfterUTS(model, LocalDate.now(), idPengguna);
        return "jadwal/mahasiswa";
    }

    @GetMapping("/dosen")
    public String viewJadwalDosen(@RequestParam(defaultValue = "0") int weekOffset,
                                  Model model,
                                  HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        String idPengguna = pengguna.getIdPengguna();

        addCommonAttributes(model, pengguna, weekOffset);
        addDaysLabel(model, weekOffset);
        addBimbinganToCalendarDosen(model, weekOffset, idPengguna);
        addBlockedJadwal(model, idPengguna);
        return "jadwal/dosen";
    }

    private void addCommonAttributes(Model model, Pengguna pengguna, int weekOffset) {
        model.addAttribute("weekOffset", weekOffset);
        model.addAttribute("currentDate", LocalDate.now().plusWeeks(weekOffset));
        model.addAttribute("currentPage", "jadwal");
        model.addAttribute("pengguna", pengguna);
    }

    private void addDaysLabel(Model model, int weekOffset) {
        List<LocalDate> tanggalList = bimbinganService.getDaysLabel(weekOffset);
        model.addAttribute("tanggalMingguIni", tanggalList);
    }

    private void addBimbinganToCalendarMahasiswa(Model model, int weekOffset, String idPengguna) {
        List<BimbinganKalender> bimbinganList = bimbinganService.findBimbinganWeekMahasiswa(idPengguna, weekOffset);
        model.addAttribute("bimbinganList", bimbinganList);
    }

    private void addBimbinganToCalendarDosen(Model model, int weekOffset, String idPengguna) {
        List<BimbinganKalender> bimbinganList = bimbinganService.findBimbinganWeekDosen(idPengguna, weekOffset);
        model.addAttribute("bimbinganList", bimbinganList);
    }

    private void addBlockedJadwal(Model model, String idPengguna) {
        model.addAttribute("blockedList", kuliahService.getKuliahListMahasiswa(idPengguna));
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

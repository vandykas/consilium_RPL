package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unpar.project.model.Pengguna;
import org.unpar.project.model.Ruangan;
import org.unpar.project.service.JadwalService;
import org.unpar.project.service.RuanganService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class JadwalApiController {

    @Autowired
    private JadwalService jadwalService;

    @Autowired
    private RuanganService ruanganService;

    @GetMapping("/ambil-jam-valid")
    @ResponseBody
    public List<LocalTime> getJamValid(@RequestParam LocalDate tanggal,
                                       @RequestParam List<String> penerima,
                                       HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        return jadwalService.getAvailableStartTimes(tanggal, pengguna.getIdPengguna(), penerima);
    }

    @GetMapping("ambil-ruang-tersedia")
    @ResponseBody
    public List<Ruangan> getRuanganTersedia(@RequestParam LocalDate tanggal,
                                            @RequestParam LocalTime mulai,
                                            @RequestParam LocalTime selesai) {
        return ruanganService.getRuanganTersedia(tanggal, mulai, selesai);
    }
}

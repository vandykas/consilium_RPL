package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.JadwalService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class JadwalApiController {

    @Autowired
    private JadwalService jadwalService;

    @GetMapping("/ambil-jam-valid")
    @ResponseBody
    public List<LocalTime> getJamValid(@RequestParam LocalDate tanggal,
                                       HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        return jadwalService.getAvailableStartTimes(tanggal, pengguna.getIdPengguna());
    }
}

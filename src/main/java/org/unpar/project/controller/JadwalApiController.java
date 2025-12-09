package org.unpar.project.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.unpar.project.service.JadwalService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/jadwal")
public class JadwalApiController {

    @Autowired
    private JadwalService jadwalService;

    // -------------------------------------------------------------
    // 1. DAPATKAN JAM MULAI YANG BISA DIPILIH
    // -------------------------------------------------------------
    @GetMapping("/available-start")
    public List<LocalTime> getAvailableStartTimes(
            @RequestParam("tanggal")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate tanggal
    ) {
        return jadwalService.getAvailableStartTimes(tanggal);
    }

    // -------------------------------------------------------------
    // 2. DAPATKAN JAM SELESAI (DARI JAM MULAI)
    // -------------------------------------------------------------
    @GetMapping("/available-end")
    public List<LocalTime> getAvailableEndTimes(
            @RequestParam("tanggal")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate tanggal,
            @RequestParam("mulai")
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            LocalTime mulai
    ) {
        return jadwalService.getAvailableEndTimes(tanggal, mulai);
    }


    // -------------------------------------------------------------
    // 3. DAPATKAN RUANGAN YANG TERSEDIA
    // -------------------------------------------------------------
    @GetMapping("/available-ruangan")
    public List<String> getAvailableRooms(
            @RequestParam("tanggal")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate tanggal,
            @RequestParam("mulai")
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            LocalTime mulai,
            @RequestParam("selesai")
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            LocalTime selesai
    ) {
        return jadwalService.getAvailableRooms(tanggal, mulai, selesai);
    }
}

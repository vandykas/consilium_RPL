package org.unpar.project.controller.api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.unpar.project.service.JadwalService;

@RestController
@RequestMapping("/api/jadwal")
public class JadwalApiController {

    @Autowired
    private JadwalService jadwalService;

    @GetMapping("/available-start")
    public List<LocalTime> getAvailableStartTimes(
            @RequestParam("tanggal")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate tanggal
    ) {
        return jadwalService.getAvailableStartTimes(tanggal);
    }

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

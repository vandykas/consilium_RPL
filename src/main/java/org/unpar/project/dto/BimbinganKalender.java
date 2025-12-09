package org.unpar.project.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BimbinganKalender {
    private int idJadwal;
    private int indexHari;
    private int jamMulai;
    private int jamSelesai;
    private LocalTime waktuMulai;
    private LocalTime waktuSelesai;
    private String ruangan;
    private LocalDate tanggal;
}

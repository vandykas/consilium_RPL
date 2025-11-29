package org.unpar.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifikasi {
    private int id;
    private boolean status;
    private String ruangan;
    private String pengirim;
    private String catatanDosen;
    private LocalDate tanggal;
    private LocalTime waktuMulai;
    private LocalTime waktuSelesai;
    private LocalDate tanggalKirim;
    private LocalTime waktuKirim;
}

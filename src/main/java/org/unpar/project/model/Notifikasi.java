package org.unpar.project.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifikasi {
    private int id;
    private Boolean status;
    private String ruangan;
    private String pengirim;
    private String catatanDosen;
    private String alasanPenolakan;
    private LocalDate tanggal;
    private LocalTime waktuMulai;
    private LocalTime waktuSelesai;
    private LocalDate tanggalKirim;
    private LocalTime waktuKirim;
}

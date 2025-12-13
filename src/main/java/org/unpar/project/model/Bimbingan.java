package org.unpar.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bimbingan {
    private int id;
    private LocalDate tanggal;
    private LocalTime waktuMulai;
    private LocalTime waktuSelesai;
    private String nomorRuangan;
    private String ruang;
    private String tugas;
    private String inti;
    private List<Mahasiswa> mahasiswa;
    private List<Dosen> dosen;
}

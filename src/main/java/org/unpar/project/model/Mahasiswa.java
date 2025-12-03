package org.unpar.project.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mahasiswa {
    private String id;
    private String nama;
    private String namaTopik;
    private List<Dosen> dosenPembimbing;
    private int sebelumUts;
    private int setelahUts;
    private LocalDate bimbinganTerakhir;
}

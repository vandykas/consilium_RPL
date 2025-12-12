package org.unpar.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jadwal implements Comparable<Jadwal> {
    private int idJadwal;
    private String hari;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private int nomorRuangan;

    public int compareTo(Jadwal other) {
        return this.getJamMulai().compareTo(other.getJamMulai());
    }
}
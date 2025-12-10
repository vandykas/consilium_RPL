package org.unpar.project.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Kuliah {
    private String idKuliah;
    private int indexHari;
    private int jamMulai;
    private int jamSelesai;
}

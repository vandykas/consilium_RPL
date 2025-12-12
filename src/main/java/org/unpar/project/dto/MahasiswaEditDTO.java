package org.unpar.project.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MahasiswaEditDTO {
    private String idMahasiswa;
    private String nama;
    private String email;
    private String kodeTopik;
    private String namaDosen;
}
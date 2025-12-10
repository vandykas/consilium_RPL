package org.unpar.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ruangan {
    private String nomorRuangan;
    private String namaRuangan;
    private boolean statusRuangan;
    private boolean jenisRuangan;
}
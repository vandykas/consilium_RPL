package org.unpar.project.model;

import lombok.Data;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dosen {
    private String id;
    private String nama;
    private List<Topik> topikList;
    private List<Mahasiswa> mahasiswaList;
}

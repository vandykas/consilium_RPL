package org.unpar.project.model;

import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mahasiswa {
    private String id;
    private int sebelumUts;
    private int setelahUts;
    private String kodeTopik;
}

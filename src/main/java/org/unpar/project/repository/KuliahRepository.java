package org.unpar.project.repository;

import org.unpar.project.dto.Kuliah;

import java.time.LocalDate;
import java.util.List;

public interface KuliahRepository {
    List<Kuliah> getKuliahListMahasiswa(String id, LocalDate mingguMulai, LocalDate mingguAkhir);
    List<Kuliah> getKuliahListDosen(String id, LocalDate mingguMulai, LocalDate mingguAkhir);
}

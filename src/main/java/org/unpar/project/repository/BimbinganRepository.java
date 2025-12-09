package org.unpar.project.repository;

import org.unpar.project.dto.BimbinganKalender;
import org.unpar.project.model.Bimbingan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BimbinganRepository {
    List<Bimbingan> findCompletedBimbinganByMahasiswa(String id);
    List<BimbinganKalender> findAllBimbingan(String id, LocalDate mingguMulai, LocalDate mingguAkhir);
    Optional<Bimbingan> findUpcomingBimbinganByMahasiswa(String id);
}

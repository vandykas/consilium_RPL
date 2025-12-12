package org.unpar.project.repository;

import org.unpar.project.model.Ruangan;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface RuanganRepository {
    List<Ruangan> getAllRuangan();
    Optional<Ruangan> getRuanganByNomor(String nomor);
    List<Ruangan> getAllRuanganKosong();
    List<Ruangan> getAllRuanganTerisi();
    List<Ruangan> getAllRuanganResmi();
    List<Ruangan> getAllRuanganNonResmi();
    List<Ruangan> findRuanganTersedia(LocalDate tanggal, LocalTime mulai, LocalTime selesai);
}

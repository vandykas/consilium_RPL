package org.unpar.project.repository;

import java.util.List;
import java.util.Optional;

import org.unpar.project.model.Ruangan;

public interface RuanganRepository {
    List<Ruangan> getAllRuangan();
    Optional<Ruangan> getRuanganByNomor(String nomor);
    List<Ruangan> getAllRuanganKosong();
    List<Ruangan> getAllRuanganTerisi();
    List<Ruangan> getAllRuanganResmi();
    List<Ruangan> getAllRuanganNonResmi();
}

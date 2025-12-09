package org.unpar.project.repository;

import java.util.List;

public interface RuanganRepository {
    List<Ruangan> getAllRuangan();
    Optional<Ruangan> getRuanganByNomor(String nomor);
    List<Ruangan> getAllRuanganKosong();
    List<Ruangan> getAllRuanganTerisi();
    List<Ruangan> getAllRuanganResmi();
    List<Ruangan> getAllRuanganNonResmi();
}

package org.unpar.project.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.model.Ruangan;
import org.unpar.project.repository.RuanganRepository;

@Service
public class RuanganService {
    @Autowired
    private RuanganRepository ruanganRepository;

    public List<Ruangan> getAll() {
        return ruanganRepository.getAllRuangan();
    }

    public Ruangan getRuanganByNomor(String nomor) {
        Optional<Ruangan> ruanganFound = ruanganRepository.getRuanganByNomor(nomor);
        return ruanganFound.isEmpty() ? null : ruanganFound.get();
    }
    
    public List<Ruangan> getAllRuanganKosong() {
        return ruanganRepository.getAllRuanganKosong();
    }

    public List<Ruangan> getAllRuanganTerisi() {
        return ruanganRepository.getAllRuanganTerisi();
    }

    public List<Ruangan> getAllRuanganResmi() {
        return ruanganRepository.getAllRuanganResmi();
    }

    public List<Ruangan> getAllRuanganNonResmi() {
        return ruanganRepository.getAllRuanganNonResmi();
    }

    public List<Ruangan> getRuanganTersedia(LocalDate tanggal, LocalTime mulai, LocalTime selesai) {
        return ruanganRepository.findRuanganTersedia(tanggal, mulai, selesai);
    }
}

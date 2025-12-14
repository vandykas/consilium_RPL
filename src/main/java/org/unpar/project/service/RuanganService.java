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

    public List<Ruangan> getRuanganTersedia(LocalDate tanggal, LocalTime mulai, LocalTime selesai) {
        return ruanganRepository.findRuanganTersedia(tanggal, mulai, selesai);
    }
}

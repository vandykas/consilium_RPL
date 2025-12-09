package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.model.Jadwal;
import org.unpar.project.repository.JadwalRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class JadwalService {

    @Autowired
    private JadwalRepository jadwalRepository;


    public List<LocalTime> getAvailableStartTimes(LocalDate tanggal) {
        // TODO: isi logic cari jam mulai yang tidak bentrok
        return jadwalRepository.findAvailableStartTimes(tanggal);
    }

    public List<LocalTime> getAvailableEndTimes(LocalDate tanggal, LocalTime mulai) {
        // TODO: generate jam selesai (mulai+1, mulai+2, mulai+3) dan filter bentrok
        return jadwalRepository.findAvailableEndTimes(tanggal, mulai);
    }

    public List<String> getAvailableRooms(LocalDate tanggal, LocalTime mulai, LocalTime selesai) {
        // TODO: return ruangan yang tidak bentrok pada jam tsb
        return jadwalRepository.findAvailableRooms(tanggal, mulai, selesai);
    }
}

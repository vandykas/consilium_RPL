package org.unpar.project.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.repository.JadwalRepository;

@Service
public class JadwalService {

    @Autowired
    private JadwalRepository jadwalRepository;


    public List<LocalTime> getAvailableStartTimes(LocalDate tanggal) {
        return jadwalRepository.findAvailableStartTimes(tanggal);
    }

    public List<LocalTime> getAvailableEndTimes(LocalDate tanggal, LocalTime mulai) {
        return jadwalRepository.findAvailableEndTimes(tanggal, mulai);
    }

    public List<String> getAvailableRooms(LocalDate tanggal, LocalTime mulai, LocalTime selesai) {
        return jadwalRepository.findAvailableRooms(tanggal, mulai, selesai);
    }
}

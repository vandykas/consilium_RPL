package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.model.Jadwal;
import org.unpar.project.repository.JadwalRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class JadwalService {

    @Autowired
    private JadwalRepository jadwalRepository;

    public List<LocalTime> getAvailableStartTimes(LocalDate tanggal, String idPengguna) {
        List<Jadwal> jadwal = jadwalRepository.findJadwalByTanggal(getHariIndonesia(tanggal), idPengguna);
        Collections.sort(jadwal);

        LocalTime current = LocalTime.of(7, 0);
        LocalTime endDay = LocalTime.of(17, 0);

        List<LocalTime> jamKosong = new ArrayList<>();

        int idx = 0;
        while (current.isBefore(endDay) && idx < jadwal.size()) {
            if (current.isBefore(jadwal.get(idx).getJamMulai())) {
                jamKosong.add(current);
                current = current.plusHours(1);
            }
            else {
                current = jadwal.get(idx).getJamSelesai();
                idx++;
            }
        }
        while (current.isBefore(endDay)) {
            jamKosong.add(current);
            current = current.plusHours(1);
        }
        return jamKosong;
    }

    public String getHariIndonesia(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return switch (day) {
            case MONDAY -> "Senin";
            case TUESDAY -> "Selasa";
            case WEDNESDAY -> "Rabu";
            case THURSDAY -> "Kamis";
            case FRIDAY -> "Jumat";
            case SATURDAY -> "Sabtu";
            case SUNDAY -> "Minggu";
        };
    }
}

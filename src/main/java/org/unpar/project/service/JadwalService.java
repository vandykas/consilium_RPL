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

    public List<LocalTime> getAvailableStartTimes(LocalDate tanggal, String idPengguna, List<String> dosenIds) {
        String hari = getHariIndonesia(tanggal);

        List<Jadwal> jadwalGabungan = new ArrayList<>(jadwalRepository.findJadwalByTanggal(hari, idPengguna));

        if (dosenIds != null) {
            for (String idDosen : dosenIds) {
                jadwalGabungan.addAll(jadwalRepository.findJadwalByTanggal(hari, String.valueOf(idDosen)));
            }
        }

        Collections.sort(jadwalGabungan);

        LocalTime current = LocalTime.of(7, 0);
        LocalTime endDay = LocalTime.of(17, 0);
        List<LocalTime> jamKosong = new ArrayList<>();

        int idx = 0;
        while (current.isBefore(endDay) && idx < jadwalGabungan.size()) {
            Jadwal j = jadwalGabungan.get(idx);

            if (current.isBefore(j.getJamMulai())) {
                jamKosong.add(current);
                current = current.plusHours(1);
            }
            else {
                if (j.getJamSelesai().isAfter(current)) {
                    current = j.getJamSelesai();
                }
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

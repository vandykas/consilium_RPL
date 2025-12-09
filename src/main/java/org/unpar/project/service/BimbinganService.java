package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.unpar.project.dto.BimbinganKalender;
import org.unpar.project.model.Bimbingan;
import org.unpar.project.repository.BimbinganRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BimbinganService {
    @Autowired
    private BimbinganRepository bimbinganRepository;

    private final int MINIMUM_BIMBINGAN_SEBELUM_UTS = 2;
    private final int MINIMUM_BIMBINGAN_SETELAH_UTS = 2;

    public Optional<Bimbingan> findUpcomingBimbinganByMahasiswa(String id) {
        return bimbinganRepository.findUpcomingBimbinganByMahasiswa(id);
    }

    public List<Bimbingan> findCompletedBimbinganByMahasiswa(String id) {
        return bimbinganRepository.findCompletedBimbinganByMahasiswa(id);
    }

    public List<LocalDate> getDaysLabel(int weekOffset) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY).plusWeeks(weekOffset);
        return List.of(
                monday,
                monday.plusDays(1),
                monday.plusDays(2),
                monday.plusDays(3),
                monday.plusDays(4)
        );
    }

    public List<BimbinganKalender> findAllBimbingan(String id, int weekOffset) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY).plusWeeks(weekOffset);
        return bimbinganRepository.findAllBimbingan(id, monday, monday.plusDays(4));
    }

    public boolean hasMetMinimumSebelumUTS(int bimbinganSebelumUTS) {
        return bimbinganSebelumUTS >= MINIMUM_BIMBINGAN_SEBELUM_UTS;
    }

    public boolean hasMetMinimumSetelahUTS(int bimbinganSetelahUTS) {
        return bimbinganSetelahUTS >= MINIMUM_BIMBINGAN_SETELAH_UTS;
    }
}

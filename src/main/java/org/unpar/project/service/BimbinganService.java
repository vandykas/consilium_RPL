package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.unpar.project.dto.BimbinganKalender;
import org.unpar.project.dto.BimbinganRequest;
import org.unpar.project.model.Bimbingan;
import org.unpar.project.repository.BimbinganRepository;
import org.unpar.project.repository.JadwalRepository;
import org.unpar.project.repository.NotifikasiRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class BimbinganService {
    @Autowired
    private BimbinganRepository bimbinganRepository;

    @Autowired
    private NotifikasiRepository notifikasiRepository;

    @Autowired
    private JadwalRepository jadwalRepository;

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

    public List<BimbinganKalender> findBimbinganWeekMahasiswa(String id, int weekOffset) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY).plusWeeks(weekOffset);
        return bimbinganRepository.findBimbinganWeekByMahasiswa(id, monday, monday.plusDays(4));
    }

    public List<BimbinganKalender> findBimbinganWeekDosen(String id, int weekOffset) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY).plusWeeks(weekOffset);
        return bimbinganRepository.findBimbinganWeekByDosen(id, monday, monday.plusDays(4));
    }

    public boolean hasMetMinimumSebelumUTS(int bimbinganSebelumUTS) {
        return bimbinganSebelumUTS >= MINIMUM_BIMBINGAN_SEBELUM_UTS;
    }

    public boolean hasMetMinimumSetelahUTS(int bimbinganSetelahUTS) {
        return bimbinganSetelahUTS >= MINIMUM_BIMBINGAN_SETELAH_UTS;
    }

    public void makeBimbingan(BimbinganRequest bimbinganRequest, String idPengguna) {
        LocalDate tanggalBimbingan = bimbinganRequest.getTanggal();
        String hariBimbingan = tanggalBimbingan.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("id", "ID"));
        Integer idJadwal = jadwalRepository.saveJadwal(hariBimbingan, bimbinganRequest.getJamMulai(),
                bimbinganRequest.getJamSelesai(), bimbinganRequest.getRuangan());

        bimbinganRepository.saveBimbingan(idJadwal, tanggalBimbingan);

        List<String> penerima = bimbinganRequest.getPenerima();
        for (String idPenerima :  penerima) {
            bimbinganRepository.savePesertaBimbinganMahasiswa(idPengguna, idPenerima, idJadwal);
            notifikasiRepository.saveNotifikasi(idJadwal, idPengguna, idPenerima);
        }
    }
}

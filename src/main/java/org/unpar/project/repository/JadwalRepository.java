package org.unpar.project.repository;

import org.unpar.project.model.Jadwal;

import java.time.LocalTime;
import java.util.List;

public interface JadwalRepository {
    List<Jadwal> findJadwalByTanggal(String hari, String idPengguna);

    Integer saveJadwal(String hariBimbingan, LocalTime jamMulai, LocalTime jamSelesai, Integer ruangan);
}

package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JadwalRepositoryImpl implements JadwalRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ---------------------------------------------------------
    // 1. GET AVAILABLE START TIMES
    // ---------------------------------------------------------
    @Override
    public List<LocalTime> findAvailableStartTimes(LocalDate tanggal) {

        // Jam operasional bimbingan → bisa disesuaikan
        LocalTime jamBuka = LocalTime.of(8, 0);
        LocalTime jamTutup = LocalTime.of(17, 0);

        // Ambil semua jadwal pada tanggal tsb
        String sql = """
            SELECT jamMulai, jamSelesai 
            FROM Jadwal
            WHERE tanggal = ?
            ORDER BY jamMulai ASC
        """;

        List<JadwalSlot> existing = jdbcTemplate.query(sql, (rs, rowNum) ->
                new JadwalSlot(
                        rs.getObject("jamMulai", LocalTime.class),
                        rs.getObject("jamSelesai", LocalTime.class)
                ),
                tanggal
        );

        List<LocalTime> available = new ArrayList<>();

        // Cek setiap 30 menit
        for (LocalTime check = jamBuka;
             check.isBefore(jamTutup);
             check = check.plusMinutes(30)) {

            boolean bentrok = false;

            for (JadwalSlot slot : existing) {
                if (check.isBefore(slot.jamSelesai) && slot.jamMulai.isBefore(check.plusHours(1))) {
                    bentrok = true;
                    break;
                }
            }

            if (!bentrok) {
                available.add(check);
            }
        }

        return available;
    }

    // ---------------------------------------------------------
    // 2. GET AVAILABLE END TIMES (max 3 hours)
    // ---------------------------------------------------------
    @Override
    public List<LocalTime> findAvailableEndTimes(LocalDate tanggal, LocalTime mulai) {

        List<LocalTime> ends = new ArrayList<>();

        // Durasi maksimal 3 jam
        for (int i = 1; i <= 3; i++) {
            LocalTime selesai = mulai.plusHours(i);

            // Cek bentrok
            if (!isTimeConflict(tanggal, mulai, selesai)) {
                ends.add(selesai);
            }
        }

        return ends;
    }

    // ---------------------------------------------------------
    // 3. GET AVAILABLE ROOMS
    // ---------------------------------------------------------
    @Override
    public List<String> findAvailableRooms(LocalDate tanggal, LocalTime mulai, LocalTime selesai) {

        // Ambil ruangan yang tidak bentrok
        String sql = """
            SELECT r.namaRuangan
            FROM Ruangan r
            WHERE r.statusRuangan = TRUE
            AND r.nomorRuangan NOT IN (
                SELECT nomorRuangan
                FROM Jadwal
                WHERE tanggal = ?
                AND jamMulai < ?
                AND jamSelesai > ?
            )
            ORDER BY r.namaRuangan
        """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getString("namaRuangan"),
                tanggal,
                Time.valueOf(selesai),
                Time.valueOf(mulai)
        );
    }

    // ---------------------------------------------------------
    // HELPER: CEK BENTROK
    // ---------------------------------------------------------
    private boolean isTimeConflict(LocalDate tanggal, LocalTime mulai, LocalTime selesai) {

        String sql = """
            SELECT COUNT(*)
            FROM Jadwal
            WHERE tanggal = ?
            AND jamMulai < ?
            AND jamSelesai > ?
        """;

        Integer count = jdbcTemplate.queryForObject(sql,
                Integer.class,
                tanggal,
                Time.valueOf(selesai),
                Time.valueOf(mulai)
        );

        return count != null && count > 0;
    }


    // ---------------------------------------------------------
    // INTERNAL CLASS UNTUK SLOT JADWAL
    // ---------------------------------------------------------
    private record JadwalSlot(LocalTime jamMulai, LocalTime jamSelesai) {}

}

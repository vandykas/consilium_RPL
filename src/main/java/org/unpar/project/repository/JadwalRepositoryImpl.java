package org.unpar.project.repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JadwalRepositoryImpl implements JadwalRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<LocalTime> findAvailableStartTimes(LocalDate tanggal) {

        LocalTime jamBuka = LocalTime.of(8, 0);
        LocalTime jamTutup = LocalTime.of(17, 0);

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

    @Override
    public List<LocalTime> findAvailableEndTimes(LocalDate tanggal, LocalTime mulai) {

        List<LocalTime> ends = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            LocalTime selesai = mulai.plusHours(i);

            if (!isTimeConflict(tanggal, mulai, selesai)) {
                ends.add(selesai);
            }
        }

        return ends;
    }

    @Override
    public List<String> findAvailableRooms(LocalDate tanggal, LocalTime mulai, LocalTime selesai) {
        String sql = """
            SELECT r.namaRuangan
            FROM Ruangan r
            WHERE r.statusRuangan = TRUE
            AND r.nomorRuangan NOT IN (W
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

    private record JadwalSlot(LocalTime jamMulai, LocalTime jamSelesai) {}

}

package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.unpar.project.dto.Kuliah;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class KuliahRepositoryImpl implements KuliahRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Kuliah> getKuliahList(String id, LocalDate mingguMulai, LocalDate mingguAkhir) {
        String sql = """
                SELECT
                    j.idJadwal,
                    j.tanggal,
                    j.jamMulai,
                    j.jamSelesai
                FROM
                    Jadwal j
                INNER JOIN KuliahMahaDosen k ON k.idJadwal = j.idJadwal
                WHERE k.idMaha = ? AND j.tanggal BETWEEN ? AND ?
                """;
        return jdbcTemplate.query(sql, this::mapRowToKuliah, id, mingguMulai, mingguAkhir);
    }

    private Kuliah mapRowToKuliah(ResultSet rs, int rowNum) throws SQLException {
        Kuliah kuliah = new Kuliah();
        kuliah.setIdKuliah(rs.getString("idJadwal"));

        LocalDate tanggal = rs.getObject("tanggal", LocalDate.class);
        kuliah.setIndexHari(calculateDayIndex(tanggal));

        kuliah.setJamMulai(rs.getObject("jamMulai", LocalTime.class).getHour());
        kuliah.setJamSelesai(rs.getObject("jamSelesai", LocalTime.class).getHour());
        return kuliah;
    }

    private int calculateDayIndex(LocalDate date) {
        if (date == null) {
            return 0;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // Monday = 1, so subtract 1 to get 0-based index
        return dayOfWeek.getValue();
    }
}

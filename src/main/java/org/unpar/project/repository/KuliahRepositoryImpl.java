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
import java.util.HashMap;
import java.util.List;

@Repository
public class KuliahRepositoryImpl implements KuliahRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Kuliah> getKuliahList(String id) {
        String sql = """
                SELECT
                    j.idJadwal,
                    j.hari,
                    j.jamMulai,
                    j.jamSelesai
                FROM
                    Jadwal j
                INNER JOIN KuliahMahaDosen k ON k.idJadwal = j.idJadwal
                WHERE k.idpengguna = ?
                """;
        return jdbcTemplate.query(sql, this::mapRowToKuliah, id);
    }

    @Override
    public void saveKuliah(int idJadwal) {
        String sql = "INSERT INTO Kuliah VALUES(?)";
        jdbcTemplate.update(sql, idJadwal);
    }

    @Override
    public void savePerkuliahan(int idJadwal, String idPengguna) {
        String sql = "INSERT INTO KuliahMahaDosen VALUES(?, ?)";
        jdbcTemplate.update(sql, idPengguna, idJadwal);
    }

    private Kuliah mapRowToKuliah(ResultSet rs, int rowNum) throws SQLException {
        Kuliah kuliah = new Kuliah();
        kuliah.setIdKuliah(rs.getString("idJadwal"));

        String hari = rs.getString("hari");
        kuliah.setIndexHari(calculateDayIndex(hari));

        kuliah.setJamMulai(rs.getObject("jamMulai", LocalTime.class).getHour());
        kuliah.setJamSelesai(rs.getObject("jamSelesai", LocalTime.class).getHour());
        return kuliah;
    }

    private int calculateDayIndex(String hari) {
        HashMap<String, Integer> hariToIndex = new HashMap<String, Integer>();
        hariToIndex.put("senin", 1);
        hariToIndex.put("selasa", 2);
        hariToIndex.put("rabu", 3);
        hariToIndex.put("kamis", 4);
        hariToIndex.put("jumat", 5);
        return hariToIndex.get(hari.toLowerCase());
    }
}

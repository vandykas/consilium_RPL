package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.unpar.project.model.Jadwal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JadwalRepositoryImpl implements JadwalRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Jadwal> findJadwalByTanggal(String hari, String idPengguna) {
        String sql = """
                SELECT
                    j.idJadwal,
                    j.hari,
                    j.jamMulai,
                    j.jamSelesai
                FROM jadwal j
                JOIN KuliahMahaDosen k ON j.idJadwal = k.idJadwal
                WHERE k.idPengguna = ? AND j.hari = ?;
                """;
        return jdbcTemplate.query(sql, this::mapRowToJadwalBasic, idPengguna, hari);
    }

    private Jadwal mapRowToJadwalBasic(ResultSet rs, int rowNum) throws SQLException {
        Jadwal jadwal = new Jadwal();
        jadwal.setIdJadwal(rs.getInt("idJadwal"));
        jadwal.setHari(rs.getString("hari"));
        jadwal.setJamMulai(rs.getObject("jamMulai", LocalTime.class));
        jadwal.setJamSelesai(rs.getObject("jamSelesai", LocalTime.class));
        return jadwal;
    }
}

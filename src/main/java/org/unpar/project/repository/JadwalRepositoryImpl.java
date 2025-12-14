package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.unpar.project.model.Jadwal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
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

    @Override
    public Integer saveJadwal(String hariBimbingan, LocalTime jamMulai, LocalTime jamSelesai, Integer ruangan) {
        String sql = "INSERT INTO Jadwal (hari, jammulai, jamselesai, nomorruangan) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            String[] keyColumn = {"idjadwal"};
            PreparedStatement ps = connection.prepareStatement(sql, keyColumn);

            // Isi parameter
            ps.setString(1, hariBimbingan);
            ps.setObject(2, jamMulai);
            ps.setObject(3, jamSelesai);
            ps.setInt(4, ruangan);

            return ps;
        }, keyHolder);

        // Ambil ID yang dihasilkan
        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
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

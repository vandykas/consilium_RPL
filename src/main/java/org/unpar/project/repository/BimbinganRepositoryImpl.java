package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.unpar.project.model.Bimbingan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BimbinganRepositoryImpl implements BimbinganRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Bimbingan> findCompletedBimbinganByMahasiswa(String id) {
        String sql = """
                SELECT
                    b.nomorBimbingan,
                    b.tanggal,
                    b.jammulai,
                    b.jamselesai,
                    b.namaruangan,
                    b.tugas,
                    b.inti
                FROM
                    viewBimbinganLengkap b
                WHERE b.idmahasiswa = ?
                AND b.statuspersetujuan = true
                AND b.tanggal < ?
                ORDER BY b.tanggal
                """;
        return jdbcTemplate.query(sql, this::mapRowToBimbingan, id, LocalDate.now());
    }

    @Override
    public Optional<Bimbingan> findUpcomingBimbinganByMahasiswa(String id) {
        String sql = """
                SELECT
                    b.nomorBimbingan,
                    b.tanggal,
                    b.jammulai,
                    b.jamselesai,
                    b.namaruangan,
                    b.tugas,
                    b.inti
                FROM
                    viewBimbinganLengkap b
                WHERE b.idmahasiswa = ?
                AND b.statuspersetujuan = true
                AND b.tanggal >= ?
                ORDER BY b.tanggal
                LIMIT 1
                """;
        try {
            Bimbingan bimbingan = jdbcTemplate.queryForObject(sql, this::mapRowToBimbingan, id, LocalDate.now());
            return Optional.ofNullable(bimbingan);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Bimbingan mapRowToBimbingan(ResultSet rs, int rowNum) throws SQLException {
        Bimbingan bimbingan = new Bimbingan();
        bimbingan.setNomor(rs.getInt("nomorBimbingan"));
        bimbingan.setTanggal(rs.getObject("tanggal", LocalDate.class));
        bimbingan.setWaktuMulai(rs.getObject("jammulai", LocalTime.class));
        bimbingan.setWaktuSelesai(rs.getObject("jamselesai", LocalTime.class));
        bimbingan.setRuang(rs.getString("namaruangan"));
        bimbingan.setTugas(rs.getString("tugas"));
        bimbingan.setInti(rs.getString("inti"));
        return bimbingan;
    }
}

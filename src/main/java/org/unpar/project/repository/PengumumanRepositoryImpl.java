package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.unpar.project.model.Pengguna;
import org.unpar.project.model.PengumumanBimbingan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PengumumanRepositoryImpl implements PengumumanRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PengumumanBimbingan mapRow(ResultSet rs, int rowNum) throws SQLException {
        boolean memenuhi = (rs.getInt("sebelumUts") + rs.getInt("setelahUts")) >= 5;

        return new PengumumanBimbingan(
                rs.getString("idMahasiswa"),
                rs.getString("npm"),
                rs.getString("nama"),
                rs.getString("judulTopik"),
                rs.getInt("sebelumUts"),
                rs.getInt("setelahUts"),
                memenuhi);
    }

    @Override
    public List<PengumumanBimbingan> getAllPengumuman() {

        String sql = """
                    SELECT
                        m.idMahasiswa,
                        split_part(p.email, '@', 1) AS npm,
                        p.nama,
                        t.judulTopik,
                        m.sebelumUts,
                        m.setelahUts
                    FROM Mahasiswa m
                    LEFT JOIN Topik t ON t.kodeTopik = m.kodeTopik
                    LEFT JOIN Pengguna p ON m.idMahasiswa = p.idPengguna
                """;

        return jdbcTemplate.query(sql, this::mapRow);
    }
}

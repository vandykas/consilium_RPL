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
                rs.getString("namaMahasiswa"),
                rs.getString("judulTopik"),
                rs.getInt("sebelumUts"),
                rs.getInt("setelahUts"),
                memenuhi
        );
    }

    @Override
    public List<PengumumanBimbingan> getAllPengumuman() {

        String sql = """
            SELECT 
                m.idMahasiswa,
                m.npm,
                m.namaMahasiswa,
                t.judulTopik,
                m.sebelumUts,
                m.setelahUts
            FROM Mahasiswa m
            LEFT JOIN Topik t ON t.kodeTopik = m.kodeTopik
        """;

        return jdbcTemplate.query(sql, this::mapRow);
    }
}

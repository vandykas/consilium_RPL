package org.unpar.project.repository;

import org.unpar.project.model.Dosen;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DosenRepositoryImpl implements DosenRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getKodeTopikDosen(String idDosen) {
        String sql = "SELECT kodeTopik FROM MembukaTopik WHERE idDosen = ?";

        List<String> kodeTopik =  jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("kodeTopik"), idDosen);

        return kodeTopik.isEmpty() ? null : kodeTopik;
    }

    @Override
    public List<Dosen> findAllDosen() {
        String sql = """
            SELECT
                d.idDosen,
                p.nama
            FROM
                dosenpembimbing d
            JOIN Pengguna p ON p.idPengguna = d.idDosen
        """;
        return jdbcTemplate.query(sql, this::mapRowToDosen);
    }

    @Override
    public List<Mahasiswa> getListMahasiswaBimbingan(String idDosen) {
        String sql = """
            SELECT p.idPengguna, p.nama
            FROM DosToStud dts
            JOIN Pengguna p ON p.idPengguna = dts.idMahasiswa
            WHERE dts.idDosen = ?
        """;

        return jdbcTemplate.query(sql, this::mapRowToMahasiswa, idDosen);
    }

    private Dosen mapRowToDosen(ResultSet rs, int rowNum) throws SQLException {
        Dosen dosen = new Dosen();
        dosen.setId(rs.getString("idDosen"));
        dosen.setNama(rs.getString("nama"));
        return dosen;
    }

    private Mahasiswa mapRowToMahasiswa(ResultSet rs, int rowNum) throws SQLException {
        Mahasiswa m = new Mahasiswa();
        m.setId(rs.getString("idPengguna"));
        m.setNama(rs.getString("nama"));
        return m;
    }
}

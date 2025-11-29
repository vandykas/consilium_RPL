package org.unpar.project.repository;

import org.unpar.project.model.Dosen;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.unpar.project.model.Pengguna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DosenRepositoryImpl implements DosenRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Dosen getDosen(String idDosen) {
        String sql = "SELECT * FROM DosenPembimbing WHERE idDosen = ?";

        List<Dosen> hasil = jdbcTemplate.query(sql, this::mapRowToDosen, idDosen);

        if (hasil.isEmpty()) return null;

        Dosen d = hasil.getFirst();

        return d;
    }

    @Override
    public List<String> getKodeTopikDosen(String idDosen) {
        String sql = "SELECT kodeTopik FROM MembukaTopik WHERE idDosen = ?";

        List<String> kodeTopik =  jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("kodeTopik"), idDosen);

        return kodeTopik.isEmpty() ? null : kodeTopik;
    }

    @Override
    public List<Pengguna> getListMahasiswaBimbingan(String idDosen) {
        String sql = """
            SELECT p.idPengguna, p.nama, p.email
            FROM DosToStud dts
            JOIN Pengguna p ON p.idPengguna = dts.idMahasiswa
            WHERE dts.idDosen = ?
        """;

        return jdbcTemplate.query(sql, this::mapRowToPengguna, idDosen);
    }

    private Dosen mapRowToDosen(ResultSet rs, int rowNum) throws SQLException {
        Dosen dosen = new Dosen();
        dosen.setId(rs.getString("idDosen"));
        return dosen;
    }

    private Pengguna mapRowToPengguna(ResultSet rs, int rowNum) throws SQLException {
        Pengguna p = new Pengguna();
        p.setIdPengguna(rs.getString("idPengguna"));
        p.setNama(rs.getString("nama"));
        p.setEmail(rs.getString("email"));
        p.setPassword(null);
        return p;
    }
}

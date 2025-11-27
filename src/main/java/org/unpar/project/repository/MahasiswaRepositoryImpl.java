package org.unpar.project.repository;

import org.unpar.project.model.Mahasiswa;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.unpar.project.model.Pengguna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MahasiswaRepositoryImpl implements MahasiswaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Mahasiswa getMahasiswa(String idMahasiswa) {

        String sql = "SELECT * FROM Mahasiswa WHERE idMahasiswa = ?";

        List<Mahasiswa> hasil = jdbcTemplate.query(sql, this::mapRowToMahasiswa, idMahasiswa);

        if (hasil.isEmpty()) return null;

        Mahasiswa m = hasil.getFirst();

        m.setKodeTopik(getKodeTopikMahasiswa(idMahasiswa));

        return m;
    }

    @Override
    public String getKodeTopikMahasiswa(String idMahasiswa) {

        String sql = "SELECT kodeTopik FROM Mahasiswa WHERE idMahasiswa = ?";

        List<String> kodeTopik =  jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("kodeTopik"), idMahasiswa);

        return kodeTopik.isEmpty() ? null : kodeTopik.getFirst();
    }

    @Override
    public List<Pengguna> getListDosenPembimbing(String idMahasiswa) {

        String sql = """
            SELECT p.idPengguna, p.nama, p.email
            FROM DosToStud dts
            JOIN Pengguna p ON p.idPengguna = dts.idDosen
            WHERE dts.idMahasiswa = ?
        """;

        return jdbcTemplate.query(sql, this::mapRowToPengguna, idMahasiswa);
    }

    private Mahasiswa mapRowToMahasiswa(ResultSet rs, int rowNum) throws SQLException {
        Mahasiswa m = new Mahasiswa();
        m.setId(rs.getString("idMahasiswa"));
        m.setSebelumUts(rs.getInt("sebelumUts"));
        m.setSetelahUts(rs.getInt("setelahUts"));
        return m;
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

package org.unpar.project.repository;

import org.unpar.project.model.Dosen;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.unpar.project.model.Mahasiswa;

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

        List<String> kodeTopik = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("kodeTopik"), idDosen);

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
    public Dosen findDosenById(String idDosen) {
        String sql = """
            SELECT
                d.idDosen,
                p.nama,
                p.email
            FROM
                dosenpembimbing d
            JOIN Pengguna p ON p.idPengguna = d.idDosen
            WHERE
                d.idDosen = ?
        """;

        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToDosenDetail, idDosen);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Mahasiswa> getListMahasiswaBimbingan(String idDosen) {
        String sql = """
                SELECT
                    dst.idMahasiswa,
                    p.nama,
                    t.judulTopik,
                    m.sebelumUTS,
                    m.setelahUTS
                FROM dostostud dst
                JOIN mahasiswa m ON dst.idMahasiswa = m.idMahasiswa
                JOIN Pengguna p ON p.idPengguna = m.idMahasiswa
                JOIN Topik t ON m.kodeTopik = t.kodeTopik
                WHERE dst.idDosen = ?
        """;
        return jdbcTemplate.query(sql, this::mapRowToMahasiswa, idDosen);
    }

    @Override
    public List<Dosen> getDosenPembimbingByMahasiswa(String id) {
        String sql = """
                SELECT
                    dts.idDosen,
                    p.nama
                FROM DosToStud dts
                JOIN Pengguna p ON p.idPengguna = dts.idDosen
                WHERE dts.idMahasiswa = ?
                """;
        return jdbcTemplate.query(sql, this::mapRowToDosen, id);
    }

    @Override
    public List<Dosen> getDosenPembimbingByBimbingan(int id) {
        String sql = """
                SELECT
                    m.idDosen,
                    p.nama
                FROM Pengguna p
                JOIN Melakukan m ON p.idPengguna = m.idDosen
                WHERE m.idJadwal = ?
                """;
        return jdbcTemplate.query(sql, this::mapRowToDosen, id);
    }

    @Override
    public Dosen getDosenPembimbingById(String idPengguna) {
        String sql = """
                SELECT
                    p.idPengguna AS idDosen,
                    p.nama
                FROM Pengguna p
                WHERE p.idPengguna = ?
                """;
        return jdbcTemplate.query(sql, this::mapRowToDosen, idPengguna).getFirst();
    }

    @Override
    public void saveDosen(String id) {
        String sql = "INSERT INTO DosenPembimbing VALUES(?)";
        jdbcTemplate.update(sql, id);
    }

    private Dosen mapRowToDosen(ResultSet rs, int rowNum) throws SQLException {
        Dosen dosen = new Dosen();
        dosen.setId(rs.getString("idDosen"));
        dosen.setNama(rs.getString("nama"));
        return dosen;
    }

    private Dosen mapRowToDosenDetail(ResultSet rs, int rowNum) throws SQLException {
        Dosen dosen = new Dosen();
        dosen.setId(rs.getString("idDosen"));
        dosen.setNama(rs.getString("nama"));
        dosen.setEmail(rs.getString("email"));
        return dosen;
    }

    private Mahasiswa mapRowToMahasiswa(ResultSet rs, int rowNum) throws SQLException {
        Mahasiswa m = new Mahasiswa();
        m.setId(rs.getString("idMahasiswa"));
        m.setNama(rs.getString("nama"));
        m.setNamaTopik(rs.getString("judulTopik"));
        m.setSebelumUts(rs.getInt("sebelumUTS"));
        m.setSetelahUts(rs.getInt("setelahUTS"));
        return m;
    }

    @Override
    public void updateDosen(Dosen dosen) {
        String sqlPengguna = "UPDATE Pengguna SET nama = ?, email = ? WHERE idPengguna = ?";
        jdbcTemplate.update(sqlPengguna, dosen.getNama(), dosen.getEmail(), dosen.getId());
    }

    @Override
    public void deleteDosen(String idDosen) {
        String sql = "DELETE FROM Pengguna WHERE idPengguna = ?";
        jdbcTemplate.update(sql, idDosen);
    }
}

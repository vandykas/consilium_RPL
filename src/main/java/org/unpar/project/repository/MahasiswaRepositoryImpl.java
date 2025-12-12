package org.unpar.project.repository;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.unpar.project.dto.MahasiswaEditDTO;
import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.unpar.project.model.Pengguna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MahasiswaRepositoryImpl implements MahasiswaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int findCounterBimbinganBeforeUTS(String idPengguna) {
        String sql = "SELECT m.sebelumUTS FROM Mahasiswa m WHERE idMahasiswa = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idPengguna);
    }

    @Override
    public int findCounterBimbinganAfterUTS(String idPengguna) {
        String sql = "SELECT m.setelahuts FROM Mahasiswa m WHERE idMahasiswa = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idPengguna);
    }

    @Override
    public List<Mahasiswa> findAllMahasiswa() {
        String sql = """
            SELECT
                m.idMahasiswa,
                p.nama,
                t.judulTopik,
                m.sebelumUTS,
                m.setelahUTS
            FROM
                Mahasiswa m
            JOIN Pengguna p ON p.idPengguna = m.idMahasiswa
            JOIN Topik t ON m.kodeTopik = t.kodeTopik
        """;
        return jdbcTemplate.query(sql, this::mapRowToMahasiswa);
    }

    @Override
    public String getKodeTopikMahasiswa(String idMahasiswa) {
        String sql = "SELECT kodeTopik FROM Mahasiswa WHERE idMahasiswa = ?";
        List<String> kodeTopik =  jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("kodeTopik"), idMahasiswa);

        return kodeTopik.isEmpty() ? null : kodeTopik.getFirst();
    }

    @Override
    public List<Dosen> getListDosenPembimbing(String idMahasiswa) {
        String sql = """
            SELECT p.idPengguna, p.nama
            FROM DosToStud dts
            JOIN Pengguna p ON p.idPengguna = dts.idDosen
            WHERE dts.idMahasiswa = ?
        """;

        return jdbcTemplate.query(sql, this::mapRowToDosen, idMahasiswa);
    }

    @Override
    public LocalDate getBimbinganTerakhir(String idMahasiswa) {
        String sql = """
            SELECT tanggal
            FROM ViewBimbinganLengkap
            WHERE idMahasiswa = ?
            ORDER BY idJadwal DESC
            LIMIT 1
        """;

        try {
            return jdbcTemplate.queryForObject(sql, LocalDate.class, idMahasiswa);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new DataAccessResourceFailureException("Mahasiswa tidak ditemukan");
        }

    }

    private Dosen mapRowToDosen(ResultSet rs, int rowNum) throws SQLException {
        Dosen d = new Dosen();
        d.setId(rs.getString("idPengguna"));
        d.setNama(rs.getString("nama"));
        return d;
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
    public MahasiswaEditDTO findMahasiswaDetailForEdit(String idMahasiswa) {
        String sql = """
            SELECT 
                m.idMahasiswa, 
                p.nama, 
                p.email, 
                m.kodeTopik 
            FROM Mahasiswa m
            JOIN Pengguna p ON m.idMahasiswa = p.idPengguna
            WHERE m.idMahasiswa = ?
        """;
        
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                MahasiswaEditDTO dto = new MahasiswaEditDTO();
                dto.setIdMahasiswa(rs.getString("idMahasiswa"));
                dto.setNama(rs.getString("nama"));
                dto.setEmail(rs.getString("email"));
                dto.setKodeTopik(rs.getString("kodeTopik"));
                return dto;
            }, idMahasiswa);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateMahasiswaData(String idMahasiswa, String nama, String email, String kodeTopik) {
        String sqlPengguna = "UPDATE Pengguna SET nama = ?, email = ? WHERE idPengguna = ?";
        jdbcTemplate.update(sqlPengguna, nama, email, idMahasiswa);

        String sqlMahasiswa = "UPDATE Mahasiswa SET kodeTopik = ? WHERE idMahasiswa = ?";
        jdbcTemplate.update(sqlMahasiswa, kodeTopik, idMahasiswa);
    }
}

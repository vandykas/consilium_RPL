package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DosToStudRepositoryImpl implements DosToStudRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<String> findMahasiswaByDosen(String idDosen) {
        String sql = "SELECT idmahasiswa FROM DosToStud WHERE idDosen = ?";
        return jdbc.queryForList(sql, String.class, idDosen);
    }

    @Override
    public List<String> findDosenByMahasiswa(String idMhs) {
        String sql = "SELECT idDosen FROM DosToStud WHERE idmahasiswa = ?";
        return jdbc.queryForList(sql, String.class, idMhs);
    }

    @Override
    public void saveMahasiswaAndDosen(String mahasiswa, String dosen) {
        String sql = "INSERT INTO DosToStud VALUES (?, ?)";
        jdbc.update(sql, dosen, mahasiswa);
    }


}

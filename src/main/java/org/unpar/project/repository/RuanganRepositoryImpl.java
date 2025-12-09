package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.unpar.project.model.Ruangan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RuanganRepositoryImpl implements RuanganRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Ruangan> findAll() {
        String sql = "SELECT nama, kapasitas FROM ruangan";

        return jdbcTemplate.query(sql, this::mapRow);
    }

    private Ruangan mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Ruangan(
        rs.getInt("nomorRuangan"),
        rs.getString("namaRuangan"),
        rs.getBoolean("statusRuangan"),
        rs.getBoolean("jenisRuangan")
        );
    }
}

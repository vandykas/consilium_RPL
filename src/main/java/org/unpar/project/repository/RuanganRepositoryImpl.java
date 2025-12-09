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
    public List<Ruangan> getAllRuangan() {
        String sql = "SELECT * FROM ruangan";
        return jdbcTemplate.query(sql, this::mapRowToRuangan);
    }

    @Override
    public Optional<Ruangan> getRuanganByNomor(String nomor) {
        String sql = "SELECT * FROM ruangan WHERE nomorRuangan = ?";
        List<Ruangan> ruanganFound = jdbcTemplate.query(sql, this::mapRowToRuangan, nomor);
        return ruanganFound.isEmpty() ? Optional.empty() : Optional.of(ruanganFound.getFirst());
    }

    @Override
    public List<Ruangan> getAllRuanganKosong() {
        String sql = "SELECT * FROM ruangan WHERE statusRuangan = ?";
        return jdbcTemplate.query(sql, this::mapRowToRuangan, false);
    }

    @Override
    public List<Ruangan> getAllRuanganTerisi() {
        String sql = "SELECT * FROM ruangan WHERE statusRuangan = ?";
        return jdbcTemplate.query(sql, this::mapRowToRuangan, true);
    }

    @Override
    public List<Ruangan> getAllRuanganResmi() {
        String sql = "SELECT * FROM ruangan WHERE jenisRuangan = ?";
        return jdbcTemplate.query(sql, this::mapRowToRuangan, false);
    }

    @Override
    public List<Ruangan> getAllRuanganNonResmi() {
        String sql = "SELECT * FROM ruangan WHERE jenisRuangan = ?";
        return jdbcTemplate.query(sql, this::mapRowToRuangan, true);
    }

    private Ruangan mapRowToRuangan(ResultSet rs, int rowNum) throws SQLException {
        Ruangan ruangan = new Ruangan();
        ruangan.setNomorRuangan(rs.getInt("nomorRuangan"));
        ruangan.setNamaRuangan(rs.getString("namaRuangan"));
        ruangan.setStatusRuangan(rs.getBoolean("statusRuangan"));
        ruangan.setJenisRuangan(rs.getBoolean("jenisRuangan"));
        return ruangan;
    }


}

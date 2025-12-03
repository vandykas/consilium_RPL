package org.unpar.project.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import org.unpar.project.model.Pengguna;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PenggunaRepositoryImpl implements PenggunaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Pengguna> findByEmail(String email) {
        String sql = "SELECT idpengguna, nama, password, email FROM Pengguna WHERE email = ?";
        List<Pengguna> results = jdbcTemplate.query(sql, this::mapRowToUser, email);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<Pengguna> findById(String id) {
        String sql = "SELECT idpengguna, nama, password, email FROM Pengguna WHERE idpengguna = ?";
        List<Pengguna> results = jdbcTemplate.query(sql, this::mapRowToUser, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Boolean isFirstLogin(String id) {
        String sql = "SELECT p.pernahLogin FROM Pengguna p WHERE p.idpengguna = ?";

        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    @Override
    public void updateLoginStatus(String id) {
        String sqlM = "UPDATE Pengguna SET pernahlogin = true WHERE idpengguna = ?";
        String sqlD = "UPDATE Pengguna SET pernahlogin = true WHERE idpengguna = ?";

        jdbcTemplate.update(sqlM, id);
        jdbcTemplate.update(sqlD, id);
    }

    @Override
    public void updatePassword(String id, String newPassword) {
        String sql = "UPDATE Pengguna SET password = ? WHERE idpengguna = ?";
        jdbcTemplate.update(sql, newPassword, id);
    }

    private Pengguna mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        Pengguna p = new Pengguna();
        p.setIdPengguna(rs.getString("idPengguna"));
        p.setNama(rs.getString("nama"));
        p.setPassword(rs.getString("password"));
        p.setEmail(rs.getString("email"));
        return p;
    }
}

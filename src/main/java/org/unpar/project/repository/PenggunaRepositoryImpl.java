package org.unpar.project.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import org.unpar.project.model.Pengguna;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;

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
        String sql = "SELECT idpengguna, nama, password, email FROM Pengguna WHERE email = ? ";
        List<Pengguna> results = jdbcTemplate.query(sql, this::mapRowToUser, email);

        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
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

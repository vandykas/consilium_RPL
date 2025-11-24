package org.unpar.project.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import org.unpar.project.model.Pengguna;
import org.unpar.project.repository.PenggunaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository  
public class JdbcPenggunaRepository implements PenggunaRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Pengguna> cariDenganEmail(String email, String password) {
        String sql = "SELECT * FROM Pengguna WHERE email = ? AND password = ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, email, password);
    }

    @Override
    public List<Pengguna> cariDenganEmail(String email) {
        String sql = "SELECT * FROM Pengguna WHERE email = ? ";
        return jdbcTemplate.query(sql, this::mapRowToUser, email);
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

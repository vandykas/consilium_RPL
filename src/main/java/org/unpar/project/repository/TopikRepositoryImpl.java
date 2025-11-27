package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.unpar.project.model.Topik;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TopikRepositoryImpl implements TopikRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getJudulTopik(String kodeTopik) {
        String sql = "select judulTopik from topik where kodeTopik = ?";
        List<String> topik =  jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("judulTopik"), kodeTopik);
        return topik.isEmpty() ? null : topik.getFirst();
    }

//    public Topik mapRowToTopik(ResultSet rs, int rowNum) throws SQLException {
//        Topik topik = new Topik();
//        topik.setKodeTopik(rs.getString("kodeTopik"));
//        topik.setJudulTopik(rs.getString("judulTopik"));
//
//        return topik;
//    }
}

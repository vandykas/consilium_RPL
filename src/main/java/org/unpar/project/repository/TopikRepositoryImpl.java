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

    @Override
    public List<Topik> findAllTopikByDosen(String id) {
        String sql = """
                SELECT
                    t.judulTopik
                FROM
                    (SELECT
                        mt.kodeTopik
                     FROM MembukaTopik mt
                     WHERE mt.iddosen = ?) mt
                JOIN Topik t ON mt.kodetopik = t.kodetopik
                """;
        return jdbcTemplate.query(sql, this::mapRowToTopik, id);
    }

    private Topik mapRowToTopik(ResultSet rs, int rowNum) throws SQLException {
        Topik topik = new Topik();
        topik.setJudulTopik(rs.getString("judulTopik"));
        return topik;
    }
}

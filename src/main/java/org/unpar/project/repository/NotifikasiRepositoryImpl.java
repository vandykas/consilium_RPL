package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.unpar.project.model.Notifikasi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class NotifikasiRepositoryImpl implements NotifikasiRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Notifikasi> findAllNotifikasiById(String id) {
        String sql = """
                SELECT
                    n.idnotifikasi,
                    n.statuspersetujuan,
                    n.tanggalkirim,
                    n.waktukirim,
                    b.inti,
                    r.namaruangan,
                    j.tanggal,
                    j.jammulai,
                    j.jamselesai,
                    p.nama AS pengirim
                FROM (
                        SELECT *
                        FROM kirimdanterima
                        WHERE idpenerima = ?
                     ) k
                JOIN notifikasi n ON k.idnotifikasi = n.idnotifikasi
                JOIN jadwal j ON n.idjadwal = j.idjadwal
                JOIN bimbingan b ON j.idjadwal = b.idjadwal
                JOIN ruangan r ON j.nomorruangan = r.nomorruangan
                JOIN pengguna p ON k.idpengirim = p.idpengguna;
                """;
        return jdbcTemplate.query(sql, this::mapRowToNotifikasi, id);
    }

    private Notifikasi mapRowToNotifikasi(ResultSet rs, int rowNum) throws SQLException {
        Notifikasi notifikasi = new Notifikasi();
        notifikasi.setId(rs.getInt("idnotifikasi"));
        notifikasi.setStatus(rs.getObject("statuspersetujuan", Boolean.class));
        notifikasi.setRuangan(rs.getString("namaruangan"));
        notifikasi.setPengirim(rs.getString("pengirim"));
        notifikasi.setCatatanDosen(rs.getString("inti"));
        notifikasi.setTanggal(rs.getObject("tanggal", LocalDate.class));
        notifikasi.setWaktuMulai(rs.getObject("jammulai", LocalTime.class));
        notifikasi.setWaktuSelesai(rs.getObject("jamselesai", LocalTime.class));
        notifikasi.setTanggalKirim(rs.getObject("tanggalkirim", LocalDate.class));
        notifikasi.setWaktuKirim(rs.getObject("waktukirim", LocalTime.class));
        return notifikasi;
    }
}

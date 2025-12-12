package org.unpar.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.unpar.project.dto.BimbinganKalender;
import org.unpar.project.model.Bimbingan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BimbinganRepositoryImpl implements BimbinganRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Bimbingan> findCompletedBimbinganByMahasiswa(String id) {
        String sql = """
                        SELECT
                            b.id_baru,
                            b.tanggal,
                            b.jammulai,
                            b.jamselesai,
                            b.namaruangan,
                            b.tugas,
                            b.inti
                        FROM (
                            SELECT
                                ROW_NUMBER() OVER (ORDER BY b.nomorBimbingan) AS id_baru,
                                b.nomorBimbingan AS id_asli,
                                b.tanggal,
                                b.jammulai,
                                b.jamselesai,
                                b.namaruangan,
                                b.tugas,
                                b.inti
                            FROM
                                viewBimbinganLengkap b
                            WHERE b.idmahasiswa = ?
                            AND b.statuspersetujuan = true
                            ORDER BY id_asli DESC) AS b
                            WHERE b.tanggal <= ?
                        """;
        return jdbcTemplate.query(sql, this::mapRowToBimbingan, id,LocalDate.now());
    }

    @Override
    public List<BimbinganKalender> findBimbinganWeekByMahasiswa(String id,
                                                    LocalDate mingguMulai,
                                                    LocalDate mingguAkhir) {
        String sql = """
                SELECT
                    v.idJadwal,
                    v.tanggal,
                    v.jamMulai,
                    v.jamSelesai,
                    v.namaRuangan
                FROM
                    ViewBimbinganLengkap v
                WHERE v.tanggal BETWEEN ? AND ? AND v.idMahasiswa = ?
                """;
        return jdbcTemplate.query(sql, this::mapRowToBimbinganKalender, mingguMulai, mingguAkhir, id);
    }


    @Override
    public List<BimbinganKalender> findBimbinganWeekByDosen(String id,
                                                               LocalDate mingguMulai,
                                                               LocalDate mingguAkhir) {
        String sql = """
                SELECT
                    v.idJadwal,
                    v.tanggal,
                    v.jamMulai,
                    v.jamSelesai,
                    v.namaRuangan
                FROM
                    ViewBimbinganLengkap v
                WHERE v.tanggal BETWEEN ? AND ? AND v.iddosen = ?
                """;
        return jdbcTemplate.query(sql, this::mapRowToBimbinganKalender, mingguMulai, mingguAkhir, id);
    }
    @Override
    public Optional<Bimbingan> findUpcomingBimbinganByMahasiswa(String id) {
        String sql = """
                        SELECT
                	        b.id_baru,
                	        b.tanggal,
                	        b.jammulai,
                	        b.jamselesai,
                	        b.namaruangan,
                	        b.tugas,
                	        b.inti
                        FROM (
                	        SELECT
                		        ROW_NUMBER() OVER (ORDER BY b.nomorBimbingan) AS id_baru,
                    	        b.nomorBimbingan AS id_asli,
                    	        b.tanggal,
                		        b.jammulai,
                		        b.jamselesai,
                		        b.namaruangan,
                		        b.tugas,
                		        b.inti
                	        FROM
                		        viewBimbinganLengkap b
                	        WHERE b.idmahasiswa = ?
                	        AND b.statuspersetujuan = true
                	        ORDER BY id_asli) AS b
                        WHERE b.tanggal >= ?
                        LIMIT 1
                    """;
        try {
            Bimbingan bimbingan = jdbcTemplate.queryForObject(sql, this::mapRowToBimbingan, id,
                    LocalDate.now());
            return Optional.ofNullable(bimbingan);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void saveBimbingan(Integer idJadwal, LocalDate tanggalBimbingan) {
        String sql = "INSERT INTO Bimbingan (idjadwal, tanggal) VALUES (?, ?)";
        jdbcTemplate.update(sql, idJadwal, tanggalBimbingan);
    }

    private Bimbingan mapRowToBimbingan(ResultSet rs, int rowNum) throws SQLException {
        Bimbingan bimbingan = new Bimbingan();
        // Menggunakan id_baru karena di-SELECT oleh query Upcoming
        bimbingan.setNomor(rs.getInt("id_baru"));
        bimbingan.setTanggal(rs.getObject("tanggal", LocalDate.class));
        bimbingan.setWaktuMulai(rs.getObject("jammulai", LocalTime.class));
        bimbingan.setWaktuSelesai(rs.getObject("jamselesai", LocalTime.class));
        bimbingan.setRuang(rs.getString("namaruangan"));
        bimbingan.setTugas(rs.getString("tugas"));
        bimbingan.setInti(rs.getString("inti"));
        return bimbingan;
    }

    private BimbinganKalender mapRowToBimbinganKalender(ResultSet rs, int rowNum) throws SQLException {
        BimbinganKalender kalender = new BimbinganKalender();

        // Set ID
        kalender.setIdJadwal(rs.getInt("idjadwal"));

        // Get tanggal dari database
        LocalDate tanggal = rs.getObject("tanggal", LocalDate.class);
        kalender.setTanggal(tanggal);

        // Calculate index hari (0 = Senin, 4 = Jumat)
        kalender.setIndexHari(calculateDayIndex(tanggal));

        // Get waktu dari database
        LocalTime waktuMulai = rs.getObject("jammulai", LocalTime.class);
        LocalTime waktuSelesai = rs.getObject("jamselesai", LocalTime.class);

        kalender.setWaktuMulai(waktuMulai);
        kalender.setWaktuSelesai(waktuSelesai);

        // Extract jam (hour) untuk grid positioning
        kalender.setJamMulai(waktuMulai.getHour());
        kalender.setJamSelesai(waktuSelesai.getHour());

        // Set ruangan
        kalender.setRuangan(rs.getString("namaruangan"));

        return kalender;
    }

    private int calculateDayIndex(LocalDate date) {
        if (date == null) {
            return 0;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // Monday = 1, so subtract 1 to get 0-based index
        return dayOfWeek.getValue();
    }
}

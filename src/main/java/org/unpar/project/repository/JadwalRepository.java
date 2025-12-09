package org.unpar.project.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface JadwalRepository {
    List<LocalTime> findAvailableStartTimes(LocalDate tanggal);
    List<LocalTime> findAvailableEndTimes(LocalDate tanggal, LocalTime mulai);
    List<String> findAvailableRooms(LocalDate tanggal, LocalTime mulai, LocalTime selesai);
}

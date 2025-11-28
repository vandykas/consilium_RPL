package org.unpar.project.repository;

import org.unpar.project.model.Bimbingan;

import java.util.List;
import java.util.Optional;

public interface BimbinganRepository {
    List<Bimbingan> findCompletedBimbinganByMahasiswa(String id);
    Optional<Bimbingan> findUpcomingBimbinganByMahasiswa(String id);
}

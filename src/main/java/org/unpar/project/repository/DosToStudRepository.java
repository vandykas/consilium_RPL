package org.unpar.project.repository;

import java.util.List;

public interface DosToStudRepository {
    List<String> findMahasiswaByDosen(String idDosen);
    List<String> findDosenByMahasiswa(String idMhs);

    void saveMahasiswaAndDosen(String mahasiswa, String dosen);
}

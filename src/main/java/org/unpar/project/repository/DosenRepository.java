package org.unpar.project.repository;


import java.util.List;

import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;

public interface DosenRepository {
    List<String> getKodeTopikDosen(String idDosen);
    List<Dosen> findAllDosen();
    List<Mahasiswa> getListMahasiswaBimbingan(String idDosen);
    Dosen findDosenById(String idDosen);
    void updateDosen(Dosen dosen);

    List<Dosen> getDosenPembimbingByMahasiswa(String id);

    List<Dosen> getDosenPembimbingByBimbingan(int id);

    Dosen getDosenPembimbingById(String idPengguna);

    void saveDosen(String id);

    void deleteDosen(String idDosen);
}

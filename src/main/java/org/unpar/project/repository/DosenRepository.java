package org.unpar.project.repository;


import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;

import java.util.List;

public interface DosenRepository {
    List<String> getKodeTopikDosen(String idDosen);
    List<Dosen> findAllDosen();
    List<Mahasiswa> getListMahasiswaBimbingan(String idDosen);

    List<Dosen> getDosenPembimbingByMahasiswa(String id);

    List<Dosen> getDosenPembimbingByBimbingan(int id);

    Dosen getDosenPembimbingById(String idPengguna);
}

package org.unpar.project.repository;

import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;

import java.time.LocalDate;
import java.util.List;

public interface MahasiswaRepository {
    int findCounterBimbinganBeforeUTS(String idPengguna);
    int findCounterBimbinganAfterUTS(String idPengguna);
    String getKodeTopikMahasiswa(String idMahasiswa);
    List<Dosen> getListDosenPembimbing(String idMahasiswa);
    List<Mahasiswa> findAllMahasiswa();
    LocalDate getBimbinganTerakhir(String idMahasiswa);
}

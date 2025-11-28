package org.unpar.project.repository;

import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;

import java.util.List;

public interface MahasiswaRepository {
    int findCounterBimbinganBeforeUTS(String idPengguna);
    int findCounterBimbinganAfterUTS(String idPengguna);
    Mahasiswa getMahasiswa(String idMahasiswa);
    String getKodeTopikMahasiswa(String idMahasiswa);
    List<Pengguna> getListDosenPembimbing(String idMahasiswa);
}

package org.unpar.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.unpar.project.dto.MahasiswaEditDTO;
import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;

public interface MahasiswaRepository {
    int findCounterBimbinganBeforeUTS(String idPengguna);
    int findCounterBimbinganAfterUTS(String idPengguna);
    String getKodeTopikMahasiswa(String idMahasiswa);
    List<Dosen> getListDosenPembimbing(String idMahasiswa);
    List<Mahasiswa> findAllMahasiswa();
    LocalDate getBimbinganTerakhir(String idMahasiswa);
    MahasiswaEditDTO findMahasiswaDetailForEdit(String idMahasiswa);
    void updateMahasiswaData(String idMahasiswa, String nama, String email, String kodeTopik);

    Mahasiswa getMahasiswaById(String id);

    List<Mahasiswa> getMahasiswaBimbinganByBimbingan(int id);

    void saveMahasiswa(String id, String kodeTopik);
}

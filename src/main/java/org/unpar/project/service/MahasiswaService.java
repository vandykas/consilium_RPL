package org.unpar.project.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.repository.MahasiswaRepository;

@Service
public class MahasiswaService {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public String getKodeTopikMahasiswa(String idMahasiswa) {
        return mahasiswaRepository.getKodeTopikMahasiswa(idMahasiswa);
    }

    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAllMahasiswa();
        for (Mahasiswa m : mahasiswaList) {
            m.setDosenPembimbing(getListDosenPembimbing(m.getId()));
        }
        return mahasiswaList;
    }

    public List<Dosen> getListDosenPembimbing(String idMahasiswa) {
        return mahasiswaRepository.getListDosenPembimbing(idMahasiswa);
    }

    public int getCounterBimbinganBeforeUTS(String idPengguna) {
        return mahasiswaRepository.findCounterBimbinganBeforeUTS(idPengguna);
    }

    public int getCounterBimbinganAfterUTS(String idPengguna) {
        return mahasiswaRepository.findCounterBimbinganAfterUTS(idPengguna);
    }

    public LocalDate getBimbinganTerakhir(String idMahasiswa) {
        return mahasiswaRepository.getBimbinganTerakhir(idMahasiswa);
    }
}
package org.unpar.project.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;
import org.unpar.project.repository.MahasiswaRepository;

@Service
public class MahasiswaService {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public Mahasiswa getMahasiswaById(String idMahasiswa) {
        return mahasiswaRepository.getMahasiswa(idMahasiswa);
    }

    public String getKodeTopikMahasiswa(String idMahasiswa) {
        return mahasiswaRepository.getKodeTopikMahasiswa(idMahasiswa);
    }

    public List<Pengguna> getListDosenPembimbing(String idMahasiswa) {
        return mahasiswaRepository.getListDosenPembimbing(idMahasiswa);
    }
}
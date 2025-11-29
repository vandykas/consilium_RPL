package org.unpar.project.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;
import org.unpar.project.repository.DosenRepository;

@Service
public class DosenService {
    @Autowired
    private DosenRepository dosenRepository;

    public Dosen getDosenById(String idDosen) {
        return dosenRepository.getDosen(idDosen);
    }

    public List<String> getKodeTopikDosen(String idDosen) {
        return dosenRepository.getKodeTopikDosen(idDosen);
    }

    public List<Pengguna> getListMahasiswaBimbingan(String idDosen) {
        return dosenRepository.getListMahasiswaBimbingan(idDosen);
    }
}

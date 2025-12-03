package org.unpar.project.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;
import org.unpar.project.repository.DosenRepository;
import org.unpar.project.repository.TopikRepository;

@Service
public class DosenService {
    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private TopikRepository topikRepository;

    public List<String> getKodeTopikDosen(String idDosen) {
        return dosenRepository.getKodeTopikDosen(idDosen);
    }

    public List<Dosen> getAllDosen() {
        List<Dosen> dosenList = dosenRepository.findAllDosen();
        for (Dosen dosen : dosenList) {
            dosen.setMahasiswaList(getListMahasiswaBimbingan(dosen.getId()));
            dosen.setTopikList(topikRepository.findAllTopikByDosen(dosen.getId()));
        }
        return dosenList;
    }

    public List<Mahasiswa> getListMahasiswaBimbingan(String idDosen) {
        return dosenRepository.getListMahasiswaBimbingan(idDosen);
    }
}

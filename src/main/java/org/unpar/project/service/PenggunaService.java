package org.unpar.project.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.unpar.project.model.Pengguna;
import org.unpar.project.repository.PenggunaRepository;

@Service
public class PenggunaService {
    @Autowired
    private PenggunaRepository penggunaRepo;

    public boolean login(String email, String password) {
        List<Pengguna> ps = penggunaRepo.cariDenganEmail(email, password);

        return ps.size() == 1;
    }

    public String getRole(String email) {
        List<Pengguna> ps = penggunaRepo.cariDenganEmail(email);
        String id = ps.get(0).getIdPengguna();
        if(id.charAt(0) == 'A') return "admin";
        else if(id.charAt(0) == 'D') return "dosen";
        else return "mahasiswa";
    }
}

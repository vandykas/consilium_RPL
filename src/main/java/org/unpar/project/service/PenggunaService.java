package org.unpar.project.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.unpar.project.model.Pengguna;
import org.unpar.project.repository.PenggunaRepository;

@Service
public class PenggunaService {
    @Autowired
    private PenggunaRepository penggunaRepo;

    public Optional<Pengguna> login(String email, String password) {
        Optional<Pengguna> penggunaDitemukan = penggunaRepo.cariDenganEmail(email);
        if (penggunaDitemukan.isEmpty()) {
            return Optional.empty();
        }
        else if (!penggunaDitemukan.get().getPassword().equals(password)) {
            return Optional.empty();
        }
        return penggunaDitemukan;
    }
}

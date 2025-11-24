package org.unpar.project.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import org.unpar.project.model.Pengguna;

@Repository
public interface PenggunaRepository {
    public List<Pengguna> cariDenganEmail(String email, String password);
    public List<Pengguna> cariDenganEmail(String email);
}

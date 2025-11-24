package main.java.org.unpar.project.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import main.java.org.unpar.project.model.Pengguna;

@Repository
public interface PenggunaRepository {
    public List<Pengguna> cariDenganEmail(String email, String password);
}

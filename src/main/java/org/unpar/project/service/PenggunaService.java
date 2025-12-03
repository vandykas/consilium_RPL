package org.unpar.project.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.unpar.project.model.Pengguna;
import org.unpar.project.repository.PenggunaRepository;

@Service
public class PenggunaService {

    Map<Character, String> idToRole = Map.of(
            'D', "dosen",
            'M', "mahasiswa",
            'A', "admin"
    );

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Pengguna> login(String email, String password) {
        Optional<Pengguna> penggunaDitemukan = penggunaRepository.findByEmail(email);
        if (penggunaDitemukan.isEmpty()) {
            return Optional.empty();
        }

        Pengguna p =  penggunaDitemukan.get();
        if (isPasswordValid(p.getIdPengguna(), password, p.getPassword())) {
            penggunaDitemukan.get().setRole(getRoleFromId(p.getIdPengguna()));
            return penggunaDitemukan;
        }
        return Optional.empty();
    }

    private boolean isPasswordValid(String id, String passwordInput, String password) {
        if (isEverLogin(id)) {
            return passwordEncoder.matches(passwordInput, password);
        }
        else {
            return passwordInput.equals(password);
        }
    }

    public String getRoleFromId(String idPengguna) {
        char roleChar = idPengguna.charAt(0);
        return idToRole.get(roleChar);
    }

    public Boolean isEverLogin(String idPengguna) {
        return penggunaRepository.isFirstLogin(idPengguna);
    }

    public void updatePassAndChangeStatus(String idPengguna, String changePasswordRequest) {
        updatePassword(idPengguna, passwordEncoder.encode(changePasswordRequest));
        updateLoginStatus(idPengguna);
    }

    public void updatePassword(String id, String newPassword) {
        penggunaRepository.updatePassword(id, newPassword);
    }

    public void updateLoginStatus(String idPengguna) {
        penggunaRepository.updateLoginStatus(idPengguna);
    }
}

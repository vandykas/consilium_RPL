package org.unpar.project.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    public Optional<Pengguna> login(String email, String password) {
        return penggunaRepository.findByEmail(email)
                .filter(pengguna -> isPasswordValid(pengguna, password));
    }

    private boolean isPasswordValid(Pengguna pengguna, String password) {
        return pengguna.getPassword() != null
                && pengguna.getPassword().equals(password);
    }

    public String getRoleFromId(String idPengguna) {
        if (idPengguna == null || idPengguna.isEmpty()) {
            throw new IllegalArgumentException("ID Pengguna tidak boleh kosong");
        }

        char roleChar = idPengguna.charAt(0);
        String role = idToRole.get(roleChar);

        if (role == null) {
            throw new IllegalArgumentException("Role tidak valid: " + roleChar);
        }

        return role;
    }

    public boolean isFirstLogin(String idPengguna) {
        return penggunaRepository.isFirstLogin(idPengguna);
    }

    public void updateLoginStatus(String idPengguna) {
        penggunaRepository.updateLoginStatus(idPengguna);
    }

    public void updatePassword(String id, String newPassword) {
        penggunaRepository.updatePassword(id, newPassword);
    }

}

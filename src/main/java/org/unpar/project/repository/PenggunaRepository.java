package org.unpar.project.repository;

import org.unpar.project.model.Pengguna;
import java.util.Optional;

public interface PenggunaRepository {
    Optional<Pengguna> findByEmail(String email);
    Optional<Pengguna> findById(String id);
    Boolean isFirstLogin(String id);
    void updateLoginStatus(String id);
    void updatePassword(String id, String newPassword);

    void savePengguna(String id, String nama, String email, String password);
}

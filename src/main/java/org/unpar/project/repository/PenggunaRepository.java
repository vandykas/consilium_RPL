package org.unpar.project.repository;

import java.util.Optional;
import org.unpar.project.model.Pengguna;

public interface PenggunaRepository {
    Optional<Pengguna> cariDenganEmail(String email);
}

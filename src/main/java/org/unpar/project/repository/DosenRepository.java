package org.unpar.project.repository;


import org.unpar.project.model.Dosen;
import org.unpar.project.model.Pengguna;

import java.util.List;

public interface DosenRepository {
    Dosen getDosen(String idMahasiswa);
    List<String> getKodeTopikDosen(String idDosen);
    List<Pengguna> getListMahasiswaBimbingan(String idDosen);
}

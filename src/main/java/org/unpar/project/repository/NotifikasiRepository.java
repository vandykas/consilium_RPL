package org.unpar.project.repository;

import org.unpar.project.model.Notifikasi;

import java.util.List;

public interface NotifikasiRepository {
    List<Notifikasi> findAllNotifikasiById(String id);
    void updateStatusNotifikasi(int id, boolean status);
}

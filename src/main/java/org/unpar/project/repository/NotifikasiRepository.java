package org.unpar.project.repository;

import java.util.List;

import org.unpar.project.model.Notifikasi;

public interface NotifikasiRepository {
    List<Notifikasi> findAllNotifikasiById(String id);
    void updateStatusNotifikasi(int id, boolean status,String alasanPenolakan);


    void saveNotifikasi(Integer idJadwal, String pengirim, String penerima);
}

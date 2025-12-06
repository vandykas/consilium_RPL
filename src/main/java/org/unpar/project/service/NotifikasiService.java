package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.model.Notifikasi;
import org.unpar.project.repository.NotifikasiRepository;

import java.util.List;

@Service
public class NotifikasiService {
    @Autowired
    private NotifikasiRepository notifikasiRepository;

    public List<Notifikasi> getAllNotifikasiById(String id) {
        return notifikasiRepository.findAllNotifikasiById(id);
    }

    public void updateStatusNotifikasi(int id, boolean status) {
        notifikasiRepository.updateStatusNotifikasi(id, status);
    }
}

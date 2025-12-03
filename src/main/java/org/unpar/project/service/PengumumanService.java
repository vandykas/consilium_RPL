package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.model.PengumumanBimbingan;
import org.unpar.project.repository.PengumumanRepository;

import java.util.List;

@Service
public class PengumumanService {

    @Autowired
    private PengumumanRepository pengumumanRepository;

    public List<PengumumanBimbingan> getAll() {
        return pengumumanRepository.getAllPengumuman();
    }
}

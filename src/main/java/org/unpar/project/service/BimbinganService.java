package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.model.Bimbingan;
import org.unpar.project.repository.BimbinganRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BimbinganService {
    @Autowired
    private BimbinganRepository bimbinganRepository;

    public Optional<Bimbingan> findUpcomingBimbinganByMahasiswa(String id) {
        return bimbinganRepository.findUpcomingBimbinganByMahasiswa(id);
    }

    public List<Bimbingan> findCompletedBimbinganByMahasiswa(String id) {
        return bimbinganRepository.findCompletedBimbinganByMahasiswa(id);
    }
}

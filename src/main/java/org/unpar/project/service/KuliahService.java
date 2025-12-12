package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.dto.Kuliah;
import org.unpar.project.repository.KuliahRepository;

import java.util.List;

@Service
public class KuliahService {
    @Autowired
    private KuliahRepository kuliahRepository;

    public List<Kuliah> getKuliahListMahasiswa(String id) {
        return kuliahRepository.getKuliahList(id);
    }
}

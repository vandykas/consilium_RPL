package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.repository.TopikRepository;

@Service
public class TopikService {
    @Autowired
    private TopikRepository topikRepository;

    public String getJudul(String kodeTopik) {
        return topikRepository.getJudulTopik(kodeTopik);
    }
}

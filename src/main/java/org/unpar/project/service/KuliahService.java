package org.unpar.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unpar.project.dto.Kuliah;
import org.unpar.project.model.Dosen;
import org.unpar.project.repository.DosenRepository;
import org.unpar.project.repository.KuliahRepository;
import org.unpar.project.repository.MahasiswaRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class KuliahService {
    @Autowired
    private KuliahRepository kuliahRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private DosenRepository dosenRepository;

    public List<Kuliah> getKuliahListMahasiswa(String id, int weekOffset) {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY).plusWeeks(weekOffset);

        List<Kuliah> kuliahGabungan = kuliahRepository.getKuliahList(id, monday, monday.plusDays(4));
        List<Dosen> dosenPembimbing = mahasiswaRepository.getListDosenPembimbing(id);
        for (Dosen dosen : dosenPembimbing) {
            kuliahGabungan.addAll(kuliahRepository.getKuliahList(dosen.getId(), monday, monday.plusDays(4)));
        }
        return kuliahGabungan;
    }
}

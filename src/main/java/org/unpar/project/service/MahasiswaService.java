package org.unpar.project.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.unpar.project.dto.MahasiswaEditDTO;
import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Topik;
import org.unpar.project.repository.DosenRepository;
import org.unpar.project.repository.MahasiswaRepository;
import org.unpar.project.repository.TopikRepository;

@Service
public class MahasiswaService {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private TopikRepository topikRepository;

    public Mahasiswa getMahasiswaInformation(String id) {
        Mahasiswa mahasiswa = mahasiswaRepository.getMahasiswaById(id);
        mahasiswa.setDosenPembimbing(dosenRepository.getDosenPembimbingByMahasiswa(id));
        return mahasiswa;
    }

    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAllMahasiswa();
        for (Mahasiswa m : mahasiswaList) {
            m.setDosenPembimbing(getListDosenPembimbing(m.getId()));
        }
        return mahasiswaList;
    }

    public List<Dosen> getListDosenPembimbing(String idMahasiswa) {
        return mahasiswaRepository.getListDosenPembimbing(idMahasiswa);
    }

    public int getCounterBimbinganBeforeUTS(String idPengguna) {
        return mahasiswaRepository.findCounterBimbinganBeforeUTS(idPengguna);
    }

    public int getCounterBimbinganAfterUTS(String idPengguna) {
        return mahasiswaRepository.findCounterBimbinganAfterUTS(idPengguna);
    }

    public MahasiswaEditDTO getDataForEdit(String idMahasiswa) {
        MahasiswaEditDTO dto = mahasiswaRepository.findMahasiswaDetailForEdit(idMahasiswa);

        if (dto != null) {
            List<Dosen> listDosen = getListDosenPembimbing(idMahasiswa);
            String namaDosenStr = listDosen.stream()
                    .map(Dosen::getNama)
                    .collect(Collectors.joining(", "));

            dto.setNamaDosen(namaDosenStr);
        }
        return dto;
    }

    @Transactional
    public void updateMahasiswa(MahasiswaEditDTO dto) {
        mahasiswaRepository.updateMahasiswaData(
                dto.getIdMahasiswa(),
                dto.getNama(),
                dto.getEmail(),
                dto.getKodeTopik()
        );
    }

    public List<Topik> getAllTopikForDropdown() {
        return topikRepository.findAllTopik();
    }

    public void processMahasiswaUpload(MultipartFile fileDataMahasiswa) {

    }
}
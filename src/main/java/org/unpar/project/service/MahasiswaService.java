package org.unpar.project.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.unpar.project.dto.MahasiswaEditDTO;
import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Topik;
import org.unpar.project.repository.*;

@Service
public class MahasiswaService {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private TopikRepository topikRepository;

    @Autowired
    private DosToStudRepository dosToStudRepository;

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

    public boolean uploadMahasiswaData(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File yang diunggah kosong.");
        }

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String line;
            boolean isHeader = true;

            while ((line = fileReader.readLine()) != null) {

                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 6) {
                    Mahasiswa mahasiswa = new Mahasiswa();

                    // csv:   idMahasiswa,nama,email,password,kodetopik,dosen1,dosen2
                    penggunaRepository.savePengguna(data[0], data[1], data[2], data[3]);
                    mahasiswaRepository.saveMahasiswa(data[0], data[4]);
                    dosToStudRepository.saveMahasiswaAndDosen(data[0], data[5]);
                }
                if (data.length >= 7) {
                    dosToStudRepository.saveMahasiswaAndDosen(data[0], data[6]);
                }
            }
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }
}
package org.unpar.project.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;
import org.unpar.project.repository.*;

@Service
public class DosenService {

    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private TopikRepository topikRepository;

    @Autowired
    private JadwalRepository jadwalRepository;

    @Autowired
    private KuliahRepository kuliahRepository;

    public Dosen getDosenById(String idDosen) {
        Dosen dosen = dosenRepository.findDosenById(idDosen);

        if (dosen != null) {
            dosen.setMahasiswaList(getListMahasiswaBimbingan(dosen.getId()));
        }
        return dosen;
    }

    public List<Dosen> getAllDosen() {
        List<Dosen> dosenList = dosenRepository.findAllDosen();
        for (Dosen dosen : dosenList) {
            dosen.setMahasiswaList(getListMahasiswaBimbingan(dosen.getId()));
            dosen.setTopikList(topikRepository.findAllTopikByDosen(dosen.getId()));
        }
        return dosenList;
    }

    public List<Mahasiswa> getListMahasiswaBimbingan(String idDosen) {
        return dosenRepository.getListMahasiswaBimbingan(idDosen);
    }

    public Dosen updateDosenData(Dosen dosen) {
        dosenRepository.updateDosen(dosen);

        return dosen;
    }

    public Dosen getDosenInformation(String idPengguna) {
        Dosen dosen = dosenRepository.getDosenPembimbingById(idPengguna);
        dosen.setTopikList(topikRepository.findAllTopikByDosen(idPengguna));
        dosen.setMahasiswaList(dosenRepository.getListMahasiswaBimbingan(idPengguna));
        return dosen;
    }

    public boolean processDataDosen(MultipartFile dataDosen, MultipartFile dataTopik, MultipartFile dataJadwal) {
        return uploadDosen(dataDosen) && uploadTopik(dataTopik) && uploadJadwal(dataJadwal);
    }

    private boolean uploadDosen(MultipartFile file) {
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
                if (data.length >= 4) {
                    // csv:   idDosen,nama,email,password
                    penggunaRepository.savePengguna(data[0], data[1], data[2], data[3]);
                    dosenRepository.saveDosen(data[0]);
                }
            }
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean uploadTopik(MultipartFile file) {
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
                if (data.length >= 3) {
                    // csv:   idDosen,kodeTopik,judulTopik
                    topikRepository.saveTopik(data[1], data[2]);
                    topikRepository.savePembukaTopik(data[0], data[1]);
                }
            }
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean uploadJadwal(MultipartFile file) {
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
                if (data.length >= 5) {
                    // csv:   hari,jamMulai,jamSelesai,nomorRuangan,idDosen
                    int idJadwal = jadwalRepository.saveJadwal(data[0], LocalTime.parse(data[1]), LocalTime.parse(data[2]), Integer.parseInt(data[3]));
                    kuliahRepository.saveKuliah(idJadwal);
                    kuliahRepository.savePerkuliahan(idJadwal, data[4]);
                }
            }
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean deleteDosen(String idDosen) {
        try {
            dosenRepository.deleteDosen(idDosen);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}

package org.unpar.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.unpar.project.service.MahasiswaService;

@Controller
public class MahasiswaController {
    @Autowired
    private MahasiswaService mahasiswaService;

    @PostMapping("/upload/mahasiswa")
    public String upload(@RequestParam MultipartFile fileDataMahasiswa) {
        boolean isInsertSuccess = mahasiswaService.uploadMahasiswaData(fileDataMahasiswa);
        return "redirect:/admin/mahasiswa";
    }

    @PostMapping("/upload/jadwal/mahasiswa")
    public String uploadJadwal(@RequestParam MultipartFile fileJadwalMahasiswa) {
        boolean isInsertSuccess = mahasiswaService.uploadMahasiswaJadwal(fileJadwalMahasiswa);
        return "redirect:/jadwal/mahasiswa";
    }
}

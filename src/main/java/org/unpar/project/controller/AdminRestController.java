package org.unpar.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.unpar.project.dto.MahasiswaEditDTO;
import org.unpar.project.service.MahasiswaService;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private MahasiswaService mahasiswaService;

    @GetMapping("/mahasiswa/{id}")
    public ResponseEntity<MahasiswaEditDTO> getMahasiswa(@PathVariable String id) {
        MahasiswaEditDTO dto = mahasiswaService.getDataForEdit(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/mahasiswa/update")
    public ResponseEntity<String> updateMahasiswa(@RequestBody MahasiswaEditDTO dto) {
        mahasiswaService.updateMahasiswa(dto);
        return ResponseEntity.ok("Success");
    }

    @RestController
@RequestMapping("/api/admin/mahasiswa")
public class MahasiswaRestController {
    
    @Autowired
    private MahasiswaService mahasiswaService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMahasiswaData(
        @RequestParam("fileDataMahasiswa") MultipartFile fileDataMahasiswa) {

        if (fileDataMahasiswa.isEmpty()) {
            return ResponseEntity.badRequest().body("File data mahasiswa wajib diunggah.");
        }
        
        try {
            mahasiswaService.processMahasiswaUpload(fileDataMahasiswa); 
            return ResponseEntity.ok("Data mahasiswa berhasil ditambahkan secara jamak.");
            
        } catch (Exception e) {
            System.err.println("Error saat proses upload mahasiswa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Gagal memproses data: " + e.getMessage());
        }
    }
}
}
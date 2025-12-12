package org.unpar.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
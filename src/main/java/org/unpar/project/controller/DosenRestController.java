package org.unpar.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unpar.project.dto.DosenEditDTO;
import org.unpar.project.model.Dosen;
import org.unpar.project.service.DosenService;

@RestController
@RequestMapping("/api/admin/dosen")
public class DosenRestController {

    @Autowired
    private DosenService dosenService;

    @GetMapping("/{idDosen}")
    public ResponseEntity<Dosen> getDosenDetail(@PathVariable("idDosen") String idDosen) {

        Dosen dosen = dosenService.getDosenById(idDosen);

        if (dosen != null) {
            return ResponseEntity.ok(dosen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idDosen}")
    public ResponseEntity<Dosen> updateDosen(
            @PathVariable("idDosen") String idDosen,
            @RequestBody DosenEditDTO updatedDosenData) {
        
        if (!idDosen.equals(updatedDosenData.getIdDosen())) { 
            return ResponseEntity.badRequest().build();
        }

        try {
            Dosen dosenToUpdate = new Dosen();
            
            dosenToUpdate.setId(updatedDosenData.getIdDosen()); 
            dosenToUpdate.setNama(updatedDosenData.getNama());
            dosenToUpdate.setEmail(updatedDosenData.getEmail());

            Dosen result = dosenService.updateDosenData(dosenToUpdate);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            System.err.println("Gagal update data dosen: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
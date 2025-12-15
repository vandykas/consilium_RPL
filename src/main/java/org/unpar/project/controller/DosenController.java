package org.unpar.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.unpar.project.model.Dosen;
import org.unpar.project.service.DosenService;

@Controller
public class DosenController {
    @Autowired
    private DosenService dosenService;

    @PostMapping("/upload/dosen")
    public String uploadDosen(@RequestParam MultipartFile fileDataDosen,
                              @RequestParam MultipartFile fileTopikDosen,
                              @RequestParam MultipartFile fileJadwalDosen) {
        boolean isInsertSuccess = dosenService.processDataDosen(fileDataDosen, fileTopikDosen, fileJadwalDosen);
        return "redirect:/admin/dosen";
    }

    @PostMapping("/delete/dosen")
    public String deleteDosen(@RequestParam("id") String idDosen, Model model) {
        if (!dosenService.deleteDosen(idDosen)) {
            model.addAttribute("error", "Dosen tidak berhasil dihapus, silakan coba lagi");
        }
        return "redirect:/admin/dosen";
    }
}

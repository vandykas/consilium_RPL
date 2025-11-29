package org.unpar.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.service.MahasiswaService;

import java.util.List;

@Controller
@RequestMapping("admin")
public class BerandaAdminController {

    @Autowired
    private MahasiswaService mahasiswaService;

    @GetMapping("/mahasiswa")
    public String viewDaftarMahasiswa(Model model) {
        addCommonAttributes(model, "adminMahasiswa");
        addTableColumn(model);
        return "beranda/adminMahasiswa";
    }

    @GetMapping("/dosen")
    public String viewDaftarDosen(Model model) {
        addCommonAttributes(model, "adminDosen");
        return "beranda/adminDosen";
    }

    private void addCommonAttributes(Model model, String page) {
        model.addAttribute("currentPage", page);
        model.addAttribute("currentRole", "admin");
    }

    private void addTableColumn(Model model) {
        List<Mahasiswa> mahasiswaList = mahasiswaService.getAllMahasiswa();
        for (Mahasiswa m : mahasiswaList) {
            m.setDosenPembimbing(mahasiswaService.getListDosenPembimbing(m.getId()));
        }
        model.addAttribute("mahasiswaList", mahasiswaList);
    }
}

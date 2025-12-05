package org.unpar.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.aspect.RequiredRole;
import org.unpar.project.service.DosenService;
import org.unpar.project.service.MahasiswaService;


@Controller
@RequestMapping("admin")
public class BerandaAdminController {

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private DosenService dosenService;

    @GetMapping("/mahasiswa")
    @RequiredRole("admin")
    public String viewDaftarMahasiswa(Model model) {
        addCommonAttributes(model, "adminMahasiswa");
        addMahasiswaTableColumn(model);
        return "beranda/adminMahasiswa";
    }

    @GetMapping("/dosen")
    @RequiredRole("admin")
    public String viewDaftarDosen(Model model) {
        addCommonAttributes(model, "adminDosen");
        addDosenTableColumn(model);
        return "beranda/adminDosen";
    }

    private void addCommonAttributes(Model model, String page) {
        model.addAttribute("currentPage", page);
        model.addAttribute("currentRole", "admin");
    }

    private void addMahasiswaTableColumn(Model model) {
        model.addAttribute("mahasiswaList", mahasiswaService.getAllMahasiswa());
    }

    private void addDosenTableColumn(Model model) {
        model.addAttribute("dosenPembimbingList", dosenService.getAllDosen());
    }
}

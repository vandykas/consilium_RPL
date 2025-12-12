package org.unpar.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.aspect.RequiredRole;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.DosenService;
import org.unpar.project.service.MahasiswaService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("admin")
public class BerandaAdminController {

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private DosenService dosenService;

    @GetMapping("/mahasiswa")
    @RequiredRole("admin")
    public String viewDaftarMahasiswa(Model model, HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, "adminMahasiswa", pengguna);
        addMahasiswaTableColumn(model);
        model.addAttribute("listTopik", mahasiswaService.getAllTopikForDropdown());
        return "admin/adminMahasiswa";
    }

    @GetMapping("/dosen")
    @RequiredRole("admin")
    public String viewDaftarDosen(Model model, HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, "adminDosen", pengguna);
        addDosenTableColumn(model);
        return "admin/adminDosen";
    }

    private void addCommonAttributes(Model model, String page, Pengguna pengguna) {
        model.addAttribute("currentPage", page);
        model.addAttribute("pengguna", pengguna);
    }

    private void addMahasiswaTableColumn(Model model) {
        model.addAttribute("mahasiswaList", mahasiswaService.getAllMahasiswa());
    }

    private void addDosenTableColumn(Model model) {
        model.addAttribute("dosenPembimbingList", dosenService.getAllDosen());
    }
}

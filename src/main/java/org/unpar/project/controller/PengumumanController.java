package org.unpar.project.controller;

import java.util.List;
import jakarta.servlet.http.HttpSession;
// import jakarta.validation.OverridesAttribute.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.PengumumanService;
import org.unpar.project.model.PengumumanBimbingan;

@Controller
@RequestMapping("/pengumuman")
public class PengumumanController {

    @Autowired
    private PengumumanService pengumumanService;

    @GetMapping("/mahasiswa")
    public String viewPengumumanMahasiswa(Model model,
            HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, pengguna);
        model.addAttribute("name", session.getAttribute("name"));
        addAllPengumumanBimbingan(model);
        return "pengumuman/pengumuman_bimbingan";
    }

    private void addAllPengumumanBimbingan(Model model) {
        List<PengumumanBimbingan> pengumumanList = pengumumanService.getAll();
        model.addAttribute("pengumumanList", pengumumanList);
    }

    @GetMapping("/dosen")
    public String viewPengumumanDosen(Model model,
            HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, pengguna);
        model.addAttribute("name", session.getAttribute("name"));
        addAllPengumumanBimbingan(model);
        return "pengumuman/pengumuman_bimbingan";
    }

    private void addCommonAttributes(Model model, Pengguna pengguna) {
        model.addAttribute("currentPage", "pengumuman");
        model.addAttribute("pengguna", pengguna);
    }
}

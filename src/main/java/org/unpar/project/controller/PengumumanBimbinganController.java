package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.PengumumanBimbingan;
import org.unpar.project.service.PengumumanService;

import java.util.List;

@Controller
@RequestMapping("/pengumuman/bimbingan")
public class PengumumanBimbinganController {

    @Autowired
    private PengumumanService pengumumanService;

    @GetMapping("/mahasiswa")
    public String viewPengumumanMahasiswa(Model model, HttpSession session) {
        addCommon(model, "mahasiswa");
        model.addAttribute("name", session.getAttribute("name"));

        List<PengumumanBimbingan> list = pengumumanService.getAll();
        model.addAttribute("pengumumanList", list);

        return "pengumuman/mahasiswa";
    }

    @GetMapping("/dosen")
    public String viewPengumumanDosen(Model model, HttpSession session) {
        addCommon(model, "dosen");
        model.addAttribute("name", session.getAttribute("name"));

        List<PengumumanBimbingan> list = pengumumanService.getAll();
        model.addAttribute("pengumumanList", list);

        return "pengumuman/dosen";
    }

    private void addCommon(Model model, String role) {
        model.addAttribute("currentRole", role);
        model.addAttribute("currentPage", "pengumuman");
    }
}

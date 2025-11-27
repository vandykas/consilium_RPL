package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.MahasiswaService;
import org.unpar.project.service.PenggunaService;
import org.unpar.project.service.TopikService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("beranda")
public class BerandaController {

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private TopikService topikService;

    @GetMapping("/mahasiswa")
    public String viewBerandaMahasiswa(Model model,
                                   HttpSession session) {
        String idPengguna = (String) session.getAttribute("id");

        addCommonAttributes(model, "mahasiswa");
        model.addAttribute("name", session.getAttribute("name"));
        addMahasiswaSpecificAttributes(model, session, idPengguna);

        return "beranda/mahasiswa";
    }

    @GetMapping("/dosen")
    public String viewBerandaDosen(Model model,
                               HttpSession session) {
        addCommonAttributes(model, "dosen");
        model.addAttribute("name", session.getAttribute("name"));
        return "beranda/dosen";
    }
    @GetMapping("/admin")
    public String viewBerandaAdmin(Model model) {
        addCommonAttributes(model, "admin");
        return "beranda/admin";
    }

    private void addMahasiswaSpecificAttributes(Model model, HttpSession session, String idPengguna) {
        model.addAttribute("topikTA", getTopikTA(idPengguna));

        List<String> dosenNames = getDosenNames(idPengguna);
        model.addAttribute("dosenPembimbing", dosenNames);
        model.addAttribute("dosenNextBimbingan", dosenNames);
    }

    private String getTopikTA(String idMahasiswa) {
        String kodeTopik = mahasiswaService.getKodeTopikMahasiswa(idMahasiswa);
        return topikService.getJudul(kodeTopik);
    }

    private List<String> getDosenNames(String idMahasiswa) {
        return mahasiswaService.getListDosenPembimbing(idMahasiswa)
                .stream()
                .map(Pengguna::getNama)
                .collect(Collectors.toList());
    }

    private void addCommonAttributes(Model model, String role) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", role);
    }
}

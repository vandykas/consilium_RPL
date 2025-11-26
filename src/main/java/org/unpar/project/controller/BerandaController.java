package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.MahasiswaService;
import org.unpar.project.service.PenggunaService;
import org.unpar.project.service.TopikService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("beranda")
public class BerandaController {

    @Autowired
    private PenggunaService penggunaService;
    @Autowired
    private MahasiswaService mahasiswaService;
    @Autowired
    private TopikService topikService;

    @GetMapping("/mahasiswa")
    public String berandaMahasiswa(Model model,
                                   HttpSession session) {
        String idPengguna = (String) session.getAttribute("id");


        // --- Ambil topik TA ---
        String kodeTopik = mahasiswaService.getKodeTopikMahasiswa(idPengguna);
        String topik = topikService.getJudul(kodeTopik);

        // buat List nama dosen dari mahasiswa terkait
        List<Pengguna> dosenList = mahasiswaService.getListDosenPembimbing(idPengguna);
        List<String> dosen = new ArrayList<>();
        for (Pengguna p : dosenList) {
            dosen.add(p.getNama());
        }


        model.addAttribute("topikTA", topik);
        model.addAttribute("dosenPembimbing", dosen);

        //model.addAttribute("bimbingan", nextBimbingan);
        model.addAttribute("dosenNextBimbingan", dosen);

        //model.addAttribute("riwayat", riwayat);

        model.addAttribute("name", session.getAttribute("name"));
        return "beranda/mahasiswa";
    }

    @GetMapping("/dosen")
    public String berandaDosen(Model model,
                               HttpSession session) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", "dosen");
        model.addAttribute("name", session.getAttribute("name"));
        return "beranda/dosen";
    }
    @GetMapping("/admin")
    public String berandaAdmin(Model model) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", "admin");
        return "beranda/admin";
    }
}

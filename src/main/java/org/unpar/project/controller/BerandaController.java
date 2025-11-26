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

import java.util.List;

@Controller
@RequestMapping("beranda")
public class BerandaController {

    @Autowired
    private PenggunaService penggunaService;
    @Autowired
    private MahasiswaService mahasiswaService;

    @GetMapping("/mahasiswa")
    public String berandaMahasiswa(Model model,
                                   HttpSession session) {
        String idPengguna = (String) session.getAttribute("id");

        Mahasiswa mhs = mahasiswaService.getMahasiswaById(idPengguna);

        // daftar dosen pembimbing (objek Pengguna → ambil nama)
        List<Pengguna> dosenList = mahasiswaService.getListDosenPembimbing(idPengguna);
        List<String> namaDosenPembimbing = dosenList.stream()
                .map(Pengguna::getNama)
                .toList();

        // --- Ambil topik TA ---
        String kodeTopik = mahasiswaService.getKodeTopikMahasiswa(idPengguna);

        List<String> dosenNext = namaDosenPembimbing;

        model.addAttribute("topikTA", kodeTopik);
        model.addAttribute("dosenPembimbing", namaDosenPembimbing);

        //model.addAttribute("bimbingan", nextBimbingan);
        model.addAttribute("dosenNextBimbingan", dosenNext);

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

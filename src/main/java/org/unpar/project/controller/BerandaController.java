package org.unpar.project.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.aspect.RequiredRole;
import org.unpar.project.model.Bimbingan;
import org.unpar.project.model.Dosen;
import org.unpar.project.model.Mahasiswa;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.BimbinganService;
import org.unpar.project.service.DosenService;
import org.unpar.project.service.MahasiswaService;
import org.unpar.project.service.TopikService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("beranda")
public class BerandaController {

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private DosenService dosenService;

    @Autowired
    private TopikService topikService;

    @Autowired
    private BimbinganService bimbinganService;

    @GetMapping("/mahasiswa")
    @RequiredRole("mahasiswa")
    public String viewBerandaMahasiswa(Model model,
            HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        String idPengguna = pengguna.getIdPengguna();

        addCommonAttributes(model, pengguna);
        addMahasiswaSpecificAttributes(model, idPengguna);
        addUpcomingBimbingan(model, idPengguna);
        addCompletedBimbingan(model, idPengguna);


        // ============================
        // ✅ DATA DUMMY UNTUK POPUP
        // TODO:
        // - hapus map dummy 
        // - ambil tanggal,jam mulai , jam selesai,namaruangan,nomorruangan,dosen,inti dan tugas dari database
        // - masukkan ke model dengan nama attribute "detailRiwayat"
        // ============================
        Map<String, Object> dummy = new HashMap<>();
        dummy.put("tanggal", LocalDate.of(2025, 10, 7));
        dummy.put("jamMulai", LocalTime.of(10, 0));
        dummy.put("jamSelesai", LocalTime.of(11, 0));
        dummy.put("namaRuangan", "Lab 4");
        dummy.put("nomorRuangan", "9015");
        dummy.put("dosen", "Vania Natali");
        dummy.put("inti", "Mengerjakan cover");
        dummy.put("tugas", "Buat latar belakang 20 halaman");

        model.addAttribute("detailRiwayat", dummy);
        // ============================

        return "beranda/mahasiswa";
    }

    @GetMapping("/dosen")
    @RequiredRole("dosen")
    public String viewBerandaDosen(Model model,
            HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        String idPengguna = pengguna.getIdPengguna();

        addCommonAttributes(model, pengguna);

        model.addAttribute("name", session.getAttribute("name"));
        addDosenSpecificAttributes(model, idPengguna);

        // ============================
        // ✅ DATA DUMMY UNTUK POPUP
        // TODO:
        // - hapus map dummy 
        // - ambil tanggal,jam mulai , jam selesai,namaruangan,nomorruangan,dosen,inti dan tugas dari database
        // - masukkan ke model dengan nama attribute "detailRiwayat"
        // ============================
        Map<String, Object> dummy = new HashMap<>();
        dummy.put("tanggal", LocalDate.of(2025, 10, 7));
        dummy.put("jamMulai", LocalTime.of(10, 0));
        dummy.put("jamSelesai", LocalTime.of(11, 0));
        dummy.put("namaRuangan", "Lab 4");
        dummy.put("nomorRuangan", "9015");
        dummy.put("dosen", "Vania Natali");
        dummy.put("inti", "Mengerjakan cover");
        dummy.put("tugas", "Buat latar belakang 20 halaman");

        model.addAttribute("detailRiwayat", dummy);
        // ============================
        return "beranda/dosen";
    }

    @GetMapping("/admin")
    @RequiredRole("admin")
    public String viewBerandaAdmin(Model model) {
        return "redirect:/admin/mahasiswa";
    }

    private void addMahasiswaSpecificAttributes(Model model, String idPengguna) {
        Mahasiswa mahasiswa = mahasiswaService.getMahasiswaInformation(idPengguna);
        model.addAttribute("mahasiswa", mahasiswa);
        addProgressBimbingan(model, mahasiswa.getSebelumUts(), mahasiswa.getSetelahUts());
    }

    private void addDosenSpecificAttributes(Model model, String idPengguna) {
        model.addAttribute("topikTA", getTopikTAForDosen(idPengguna));

        List<Mahasiswa> mahasiswaList = getMahasiswaNames(idPengguna);
        for (Mahasiswa m : mahasiswaList) {
            m.setNamaTopik(getTopikTA(m.getId()));
            m.setSebelumUts(mahasiswaService.getCounterBimbinganBeforeUTS(m.getId()));
            m.setSetelahUts(mahasiswaService.getCounterBimbinganAfterUTS(m.getId()));
            m.setBimbinganTerakhir(getBimbinganTerakhirMahasiswa(m.getId()));
        }
        model.addAttribute("mahasiswaList", mahasiswaList);

    }

    private String getTopikTA(String idMahasiswa) {
        String kodeTopik = mahasiswaService.getKodeTopikMahasiswa(idMahasiswa);
        return topikService.getJudul(kodeTopik);
    }

    private List<String> getTopikTAForDosen(String idDosen) {
        List<String> kodeTopik = dosenService.getKodeTopikDosen(idDosen);

        List<String> judulTopik = new ArrayList<>();
        for (String kode : kodeTopik) {
            judulTopik.add(topikService.getJudul(kode));
        }

        return judulTopik;
    }

    private List<String> getDosenNames(String idMahasiswa) {
        return mahasiswaService.getListDosenPembimbing(idMahasiswa)
                .stream()
                .map(Dosen::getNama)
                .collect(Collectors.toList());
    }

    private List<Mahasiswa> getMahasiswaNames(String idDosen) {
        return dosenService.getListMahasiswaBimbingan(idDosen);
    }

    private LocalDate getBimbinganTerakhirMahasiswa(String idMahasiswa) {
        return mahasiswaService.getBimbinganTerakhir(idMahasiswa);
    }

    private void addUpcomingBimbingan(Model model, String id) {
        Optional<Bimbingan> upcomingBimbingan = bimbinganService.findUpcomingBimbinganByMahasiswa(id);

        if (upcomingBimbingan.isPresent()) {
            model.addAttribute("bimbinganMendatang", upcomingBimbingan.get());
        } else {
            model.addAttribute("bimbinganMendatang", createEmptyBimbingan());
        }
    }

    private void addCompletedBimbingan(Model model, String id) {
        model.addAttribute("riwayat", bimbinganService.findCompletedBimbinganByMahasiswa(id));
    }

    private void addProgressBimbingan(Model model, int countBimbinganSebelumUTS, int countBimbinganSetelahUTS) {
        boolean isMemenuhiSebelumUTS = bimbinganService.hasMetMinimumSebelumUTS(countBimbinganSebelumUTS);
        boolean isMemenuhiSetelahUTS = bimbinganService.hasMetMinimumSetelahUTS(countBimbinganSetelahUTS);

        model.addAttribute("isMemenuhiSebelumUTS", isMemenuhiSebelumUTS);
        model.addAttribute("isMemenuhiSetelahUTS", isMemenuhiSetelahUTS);
    }

    private Bimbingan createEmptyBimbingan() {
        return new Bimbingan();
    }

    private void addCommonAttributes(Model model, Pengguna pengguna) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("pengguna", pengguna);
    }
}

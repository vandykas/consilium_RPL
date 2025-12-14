package org.unpar.project.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("beranda")
public class BerandaController {

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private DosenService dosenService;

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
        addUpcomingBimbinganMahasiswa(model, idPengguna);
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
        addDosenSpecificAttributes(model, idPengguna);
        addUpcomingBimbinganDosen(model, idPengguna);

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
        Dosen dosen = dosenService.getDosenInformation(idPengguna);
        model.addAttribute("dosen", dosen);
    }

    private void addUpcomingBimbinganMahasiswa(Model model, String id) {
        Optional<Bimbingan> upcomingBimbingan = bimbinganService.findUpcomingBimbinganByMahasiswa(id);

        if (upcomingBimbingan.isPresent()) {
            model.addAttribute("bimbinganMendatang", upcomingBimbingan.get());
        } else {
            model.addAttribute("bimbinganMendatang", createEmptyBimbingan());
        }
    }

    private void addUpcomingBimbinganDosen(Model model, String id) {
        Optional<Bimbingan> upcomingBimbingan = bimbinganService.findUpcomingBimbinganByDosen(id);

        if (upcomingBimbingan.isPresent()) {
            model.addAttribute("bimbinganMendatang", upcomingBimbingan.get());
        } else {
            model.addAttribute("bimbinganMendatang", createEmptyBimbingan());
        }
    }

    private void addCompletedBimbingan(Model model, String id) {
        model.addAttribute("riwayat", bimbinganService.findCompletedBimbinganByMahasiswa(id));
    }

    private Bimbingan createEmptyBimbingan() {
        return new Bimbingan();
    }


    private void addProgressBimbingan(Model model, int countBimbinganSebelumUTS, int countBimbinganSetelahUTS) {
        boolean isMemenuhiSebelumUTS = bimbinganService.hasMetMinimumSebelumUTS(countBimbinganSebelumUTS);
        boolean isMemenuhiSetelahUTS = bimbinganService.hasMetMinimumSetelahUTS(countBimbinganSetelahUTS);

        model.addAttribute("isMemenuhiSebelumUTS", isMemenuhiSebelumUTS);
        model.addAttribute("isMemenuhiSetelahUTS", isMemenuhiSetelahUTS);
    }

    private void addCommonAttributes(Model model, Pengguna pengguna) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("pengguna", pengguna);
    }
}

package org.unpar.project.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.model.Bimbingan;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.BimbinganService;
import org.unpar.project.service.DosenService;
import org.unpar.project.service.MahasiswaService;
import org.unpar.project.service.TopikService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String viewBerandaMahasiswa(Model model,
                                   HttpSession session) {
        String idPengguna = (String) session.getAttribute("id");

        addCommonAttributes(model, "mahasiswa");
        addUpcomingBimbingan(model, idPengguna);
        addCompletedBimbingan(model, idPengguna);
        addProgressBimbingan(model, idPengguna);

        model.addAttribute("name", session.getAttribute("name"));
        addMahasiswaSpecificAttributes(model, session, idPengguna);

        return "beranda/mahasiswa";
    }

    @GetMapping("/dosen")
    public String viewBerandaDosen(Model model,
                               HttpSession session) {
        String idPengguna = (String) session.getAttribute("id");

        addCommonAttributes(model, "dosen");

        model.addAttribute("name", session.getAttribute("name"));
        addDosenSpecificAttributes(model, session, idPengguna);
        return "beranda/dosen";
    }

    @GetMapping("/admin")
    public String viewBerandaAdmin(Model model) {
        return "redirect:/admin/mahasiswa";
    }

    private void addMahasiswaSpecificAttributes(Model model, HttpSession session, String idPengguna) {
        model.addAttribute("topikTA", getTopikTA(idPengguna));

        List<String> dosenNames = getDosenNames(idPengguna);
        model.addAttribute("dosenPembimbing", dosenNames);
        model.addAttribute("dosenNextBimbingan", dosenNames);
    }

    private void addDosenSpecificAttributes(Model model, HttpSession session, String idPengguna) {
        model.addAttribute("topikTA", getTopikTAForDosen(idPengguna));

//        List<String> mahasiswaNames = getMahasiswaNames(idPengguna);
//        model.addAttribute("mahasiswaList", mahasiswaNames);
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
                .map(Pengguna::getNama)
                .collect(Collectors.toList());
    }

    private List<String> getMahasiswaNames(String idDosen) {
        return dosenService.getListMahasiswaBimbingan(idDosen)
                .stream()
                .map(Pengguna::getNama)
                .collect(Collectors.toList());
    }

    private void addUpcomingBimbingan(Model model, String id) {
        Optional<Bimbingan> upcomingBimbingan = bimbinganService.findUpcomingBimbinganByMahasiswa(id);

        if (upcomingBimbingan.isPresent()) {
            model.addAttribute("bimbingan", upcomingBimbingan.get());
        }
        else {
            model.addAttribute("bimbingan", createEmptyBimbingan());
        }
    }

    private void addCompletedBimbingan(Model model, String id) {
        model.addAttribute("riwayat", bimbinganService.findCompletedBimbinganByMahasiswa(id));
    }

    private void addProgressBimbingan(Model model, String id) {
        model.addAttribute("sebelumUTS", mahasiswaService.getCounterBimbinganBeforeUTS(id));
        model.addAttribute("setelahUTS", mahasiswaService.getCounterBimbinganAfterUTS(id));
    }

    private Bimbingan createEmptyBimbingan() {
        return new Bimbingan();
    }

    private void addCommonAttributes(Model model, String role) {
        model.addAttribute("currentPage", "beranda");
        model.addAttribute("currentRole", role);
    }
}

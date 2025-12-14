package org.unpar.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unpar.project.aspect.RequiredRole;
import org.unpar.project.model.Pengguna;
import org.unpar.project.service.NotifikasiService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("notifikasi")
public class NotifikasiController {
    @Autowired
    private NotifikasiService notifikasiService;

    @GetMapping("/mahasiswa")
    @RequiredRole("mahasiswa")
    public String viewNotifikasiMahasiswa(Model model,
            HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, pengguna);
        return "notifikasi/notifikasi";
    }

    @GetMapping("/dosen")
    @RequiredRole("dosen")
    public String viewNotifikasiDosen(Model model,
            HttpSession session) {
        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        addCommonAttributes(model, pengguna);
        return "notifikasi/notifikasi";
    }

    @PostMapping("/updateNotifikasi")
    public String updateNotifikasi(HttpServletRequest request,
            HttpSession session) {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean status = request.getParameter("status").equals("OK");
        String alasanPenolakan = request.getParameter("alasan");
        notifikasiService.updateStatusNotifikasi(id, status, alasanPenolakan);

        Pengguna pengguna = (Pengguna) session.getAttribute("pengguna");
        return "redirect:/notifikasi/" + pengguna.getRole();
    }

    private void addCommonAttributes(Model model, Pengguna pengguna) {
        model.addAttribute("currentPage", "notifikasi");
        model.addAttribute("pengguna", pengguna);
        model.addAttribute("notifications", notifikasiService.getAllNotifikasiById(pengguna.getIdPengguna()));
    }
}

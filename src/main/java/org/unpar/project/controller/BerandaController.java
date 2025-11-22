package org.unpar.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("beranda")
public class BerandaController {
    @GetMapping("/")
    public String beranda(Model model) {
        model.addAttribute("currentPage", "beranda");
        return "beranda/index";
    }
}

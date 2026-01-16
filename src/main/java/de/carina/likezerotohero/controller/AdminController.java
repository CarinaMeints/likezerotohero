package de.carina.likezerotohero.controller;

import de.carina.likezerotohero.service.EmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmissionService emissionService;

    @GetMapping
    public String adminPanel(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "admin";
    }

    @PostMapping("/add")
    public String addEmission(
            @RequestParam String countryCode,
            @RequestParam LocalDate emissionDate,
            @RequestParam Double co2Kilotons,
            Authentication authentication) {

        String username = authentication.getName();

        emissionService.addEmission(countryCode, emissionDate, co2Kilotons, username);

        return "redirect:/admin";
    }
}
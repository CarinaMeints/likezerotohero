package de.carina.likezerotohero.controller;

import de.carina.likezerotohero.repository.CountryRepository;
import de.carina.likezerotohero.service.EmissionService;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CountryRepository countryRepository;
    private final EmissionService emissionService;

    public AdminController(CountryRepository countryRepository,
                           EmissionService emissionService) {
        this.countryRepository = countryRepository;
        this.emissionService = emissionService;
    }

    @GetMapping
    public String adminPanel(Model model, Authentication authentication) {
        String username = authentication.getName();

        model.addAttribute("username", username);
        model.addAttribute("emissions", emissionService.findByUser(username));

        model.addAttribute("countries", countryRepository.findAll());

        return "admin";
    }

    @PostMapping("/add")
    public String addEmission(
            @RequestParam String countryCode,
            @RequestParam LocalDate emissionDate,
            @RequestParam Double co2Kilotons,
            Authentication authentication) {

        emissionService.addEmission(
                countryCode, emissionDate, co2Kilotons,
                authentication.getName()
        );

        return "redirect:/admin";
    }
}
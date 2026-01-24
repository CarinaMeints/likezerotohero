package de.carina.likezerotohero.controller;

import de.carina.likezerotohero.model.Country;
import de.carina.likezerotohero.model.Emission;
import de.carina.likezerotohero.repository.CountryRepository;
import de.carina.likezerotohero.repository.EmissionRepository;
import de.carina.likezerotohero.service.EmissionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CountryRepository countryRepository;
    private final EmissionService emissionService;
    private final EmissionRepository emissionRepository;

    public AdminController(
            CountryRepository countryRepository,
            EmissionRepository emissionRepository,
            EmissionService emissionService
    ) {
        this.countryRepository = countryRepository;
        this.emissionRepository = emissionRepository;
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

    private boolean validateEmissionData(
            Double emission,
            LocalDate date,
            RedirectAttributes redirectAttributes
    ) {
        if (emission < 0 || emission > 200000) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "CO2-Wert muss zwischen 0 und 200.000 kt liegen!"
            );
            return false;
        }

        if (date.isAfter(LocalDate.now())) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Datum darf nicht in der Zukunft liegen!"
            );
            return false;
        }

        return true;
    }

    @PostMapping("/add")
    public String addEmission(
            @RequestParam String countryCode,
            @RequestParam LocalDate emissionDate,
            @RequestParam Double co2Kilotons,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        if (!validateEmissionData(co2Kilotons, emissionDate, redirectAttributes)) {
            return "redirect:/admin";
        }

        try {
            emissionService.addEmission(
                    countryCode, emissionDate, co2Kilotons,
                    authentication.getName()
            );
            redirectAttributes.addFlashAttribute("success",
                    "Emission erfolgreich hinzugefügt!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Fehler beim Speichern: " + e.getMessage());
        }

        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateEmission(
            @RequestParam Long id,
            @RequestParam String countryCode,
            @RequestParam LocalDate emissionDate,
            @RequestParam Double emission,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        Emission e = emissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Emission nicht gefunden"));

        if (!validateEmissionData(emission, emissionDate, redirectAttributes)) {
            return "redirect:/admin";
        }

        Country country = countryRepository.findByCode(countryCode);

        e.setCountry(country);
        e.setEmission(emission);
        e.setEmissionDate(emissionDate);

        try {
            emissionRepository.save(e);
            redirectAttributes.addFlashAttribute("success",
                    "Emission erfolgreich aktualisiert!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error",
                    "Fehler beim Aktualisieren: " + ex.getMessage());
        }

        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteEmission(@PathVariable Long id) {

        Emission e = emissionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Emission nicht gefunden"
                        ));

        emissionRepository.delete(e);

        return "OK";
    }
}
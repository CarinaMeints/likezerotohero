package de.carina.likezerotohero.controller;

import de.carina.likezerotohero.repository.CountryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    private final CountryRepository countryRepository;

    public PublicController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("countries", countryRepository.findAll());

        try {
            String detectedCountry = request.getLocale().getCountry();
            System.out.println(detectedCountry);

            model.addAttribute("detectedCountry", detectedCountry);
        } catch (Exception e) {
            System.out.println("Geo detection failed: " + e.getMessage());
            model.addAttribute("detectedCountry", null);
        }

        return "index";
    }
}
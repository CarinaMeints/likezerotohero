package de.carina.likezerotohero.controller;

import org.springframework.ui.Model;
import de.carina.likezerotohero.repository.CountryRepository;
import de.carina.likezerotohero.repository.EmissionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private final EmissionRepository emissionRepository;
    private final CountryRepository countryRepository;

    public SearchController(EmissionRepository emissionRepository, CountryRepository countryRepository) {
        this.emissionRepository = emissionRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/search")
    public String search(@RequestParam String countryCode, Model model) {

        var latest = emissionRepository
                .findFirstByCountryCodeOrderByEmissionDateDesc(countryCode);

        model.addAttribute("countryCode", countryCode);
        model.addAttribute("emission", latest.orElse(null));

        return "index";
    }
}

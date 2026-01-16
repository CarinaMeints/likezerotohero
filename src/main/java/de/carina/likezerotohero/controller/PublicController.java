package de.carina.likezerotohero.controller;

import de.carina.likezerotohero.repository.CountryRepository;
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
    public String index(Model model) {
        model.addAttribute("countries", countryRepository.findAll());
        return "index";
    }
}
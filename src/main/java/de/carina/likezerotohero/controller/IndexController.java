package de.carina.likezerotohero.controller;

import de.carina.likezerotohero.repository.CountryRepository;
import de.carina.likezerotohero.repository.EmissionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class IndexController {

    private final CountryRepository countryRepository;
    private final EmissionRepository emissionRepository;

    public IndexController(CountryRepository countryRepository, EmissionRepository emissionRepository) {
        this.countryRepository = countryRepository;
        this.emissionRepository = emissionRepository;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("countries", countryRepository.findAll());

        try {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isBlank()) {
                ip = request.getRemoteAddr();
            }

            RestTemplate rest = new RestTemplate();
            Map data = rest.getForObject("https://ipapi.co/" + ip + "/json/", Map.class);

            String detectedCountry = (String) data.get("country");

            model.addAttribute("detectedCountry", detectedCountry);

        } catch (Exception e) {
            model.addAttribute("detectedCountry", null);
        }

        return "index";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam String countryCode,
            Model model
    ) {
        model.addAttribute("countries",
                countryRepository.findAll());
        model.addAttribute("countryCode", countryCode);

        var latest = emissionRepository
                .findFirstByCountryCodeOrderByEmissionDateDesc(
                        countryCode);

        model.addAttribute("emission",
                latest.orElse(null));

        var country = countryRepository.findByCode(
                countryCode);

        model.addAttribute("countryName",
                country != null
                        ? country.getCountryName()
                        : null);

        return "index";
    }
}
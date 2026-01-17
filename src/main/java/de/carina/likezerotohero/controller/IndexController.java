package de.carina.likezerotohero.controller;

import de.carina.likezerotohero.repository.CountryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class IndexController {

    private final CountryRepository countryRepository;

    public IndexController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("countries", countryRepository.findAll());

        try {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isBlank()) {
                ip = request.getRemoteAddr();
            }
            System.out.println("Client IP = " + ip);

            RestTemplate rest = new RestTemplate();
            Map data = rest.getForObject("https://ipapi.co/" + ip + "/json/", Map.class);

            String detectedCountry = (String) data.get("country");

            System.out.println(detectedCountry);

            model.addAttribute("detectedCountry", detectedCountry);

            model.addAttribute("detectedCountry", detectedCountry);
        } catch (Exception e) {
            System.out.println("Geo detection failed: " + e.getMessage());
            model.addAttribute("detectedCountry", null);
        }

        return "index";
    }
}
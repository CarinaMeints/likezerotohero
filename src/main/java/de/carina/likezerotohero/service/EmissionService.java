package de.carina.likezerotohero.service;

import de.carina.likezerotohero.model.Country;
import de.carina.likezerotohero.model.Emission;
import de.carina.likezerotohero.repository.CountryRepository;
import de.carina.likezerotohero.repository.EmissionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class EmissionService {

    private final CountryRepository countryRepository;
    private final EmissionRepository emissionRepository;

    public EmissionService(CountryRepository countryRepository,
                           EmissionRepository emissionRepository) {
        this.countryRepository = countryRepository;
        this.emissionRepository = emissionRepository;
    }

    public void addEmission(String countryCode, LocalDate date, Double emissionValue, String addedBy) {

        Country country = countryRepository.findByCode(countryCode);
        if (country == null) {
            throw new RuntimeException("Country not found: " + countryCode);
        }

        Emission emission = new Emission();
        emission.setEmission(emissionValue);
        emission.setEmissionDate(date);   // <- LocalDate
        emission.setAddedBy(addedBy);
        emission.setCountry(country);

        emissionRepository.save(emission);
    }
}
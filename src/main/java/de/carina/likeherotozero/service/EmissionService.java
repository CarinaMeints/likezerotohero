package de.carina.likeherotozero.service;

import de.carina.likeherotozero.model.Country;
import de.carina.likeherotozero.model.Emission;
import de.carina.likeherotozero.repository.CountryRepository;
import de.carina.likeherotozero.repository.EmissionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmissionService {

    private final EmissionRepository emissionRepository;
    private final CountryRepository countryRepository;

    public EmissionService(EmissionRepository emissionRepository,
                           CountryRepository countryRepository) {
        this.emissionRepository = emissionRepository;
        this.countryRepository = countryRepository;
    }

    public void addEmission(String countryCode, LocalDate date, Double emissionValue, String addedBy) {
        Country country = countryRepository.findByCode(countryCode);

        Emission e = new Emission();
        e.setEmission(emissionValue);
        e.setEmissionDate(date);
        e.setAddedBy(addedBy);
        e.setCountry(country);

        emissionRepository.save(e);
    }

    public List<Emission> findByUser(String username) {
        return emissionRepository.findByAddedBy(username);
    }
}
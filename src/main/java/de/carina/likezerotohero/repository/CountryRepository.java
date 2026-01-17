package de.carina.likezerotohero.repository;

import de.carina.likezerotohero.model.Country;
import de.carina.likezerotohero.model.Emission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {
    Country findByCode(String code);
}
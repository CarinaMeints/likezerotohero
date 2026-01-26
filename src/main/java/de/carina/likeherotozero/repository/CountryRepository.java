package de.carina.likeherotozero.repository;

import de.carina.likeherotozero.model.Country;
import de.carina.likeherotozero.model.Emission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {
    Country findByCode(String code);
}
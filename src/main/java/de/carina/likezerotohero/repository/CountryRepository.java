package de.carina.likezerotohero.repository;

import de.carina.likezerotohero.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
    Country findByCode(String code);
}
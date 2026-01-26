package de.carina.likeherotozero.repository;

import de.carina.likeherotozero.model.Emission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmissionRepository extends JpaRepository<Emission, Long> {
    List<Emission> findByAddedBy(String addedBy);
    Optional<Emission> findFirstByCountryCodeOrderByEmissionDateDesc(String code);
}


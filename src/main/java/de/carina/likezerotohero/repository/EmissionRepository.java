package de.carina.likezerotohero.repository;

import de.carina.likezerotohero.model.Emission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmissionRepository extends JpaRepository<Emission, Long> {
}
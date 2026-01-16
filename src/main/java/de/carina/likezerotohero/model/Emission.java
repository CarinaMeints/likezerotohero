package de.carina.likezerotohero.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "emissions")
public class Emission {
    @GeneratedValue @Id private Long id;
    private double emission;
    private String addedBy;
    private LocalDate emissionDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code")
    private Country country;

    public Emission() {

    }
}
package de.carina.likezerotohero.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Country {
    @Id @Column(length = 2)
    private String code;
    @Column(name = "country_name")
    private String countryName;
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Emission> emissions = new ArrayList<>();
    public Country() {}

    public Country(String code, String countryName) {
        this.code = code;
        this.countryName = countryName;
    }
}
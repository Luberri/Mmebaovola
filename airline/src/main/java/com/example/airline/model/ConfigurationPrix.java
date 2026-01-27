package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "configuration_prix")
@Data
public class ConfigurationPrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal prix;

    @OneToMany(mappedBy = "configurationPrix")
    private List<PubContrat> contrats;
}

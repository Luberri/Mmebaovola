package com.example.airline.model;

import com.example.airline.model.enums.EtatAvion;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "avion")
@Data
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modele;
    private Integer capaciteTotale;
    private String immatriculation;

    @Enumerated(EnumType.STRING)
    private EtatAvion etat;

    @ManyToOne
    @JoinColumn(name = "id_compagnie")
    private CompagnieAerienne compagnie;

    public Long getId() {
        return id;
    }

    public String getModele() {
        return modele;
    }

    public Integer getCapaciteTotale() {
        return capaciteTotale;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public EtatAvion getEtat() {
        return etat;
    }

    public CompagnieAerienne getCompagnie() {
        return compagnie;
    }
}

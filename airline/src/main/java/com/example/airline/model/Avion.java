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
}

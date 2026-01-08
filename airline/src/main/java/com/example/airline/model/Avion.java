package com.example.airline.model;

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
    private String etat;

    @ManyToOne
    @JoinColumn(name = "id_compagnie")
    private CompagnieAerienne compagnie;
}

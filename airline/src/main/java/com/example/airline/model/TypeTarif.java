package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "type_tarif")
@Data
public class TypeTarif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // NORMAL, ENFANT, ETUDIANT, PROMO

    private String libelle;

    private String description;
}
package com.example.airline.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "classe_voyage")
@Data
public class ClasseVoyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private String description;
    public BigDecimal getPrix() {
        // Exemple : Retourner un prix par défaut ou une valeur calculée
        return BigDecimal.valueOf(1000); // Remplacez par la logique réelle
    }
}

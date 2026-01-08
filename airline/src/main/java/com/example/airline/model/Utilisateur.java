package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "utilisateur")
@Data
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String motDePasse;
    private String roleSysteme; // ADMIN, AGENT

    @ManyToOne
    @JoinColumn(name = "id_employe", nullable = true)
    private Employe employe;
}

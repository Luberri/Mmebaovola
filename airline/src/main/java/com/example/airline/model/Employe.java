package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

import java.util.List;

@Entity
@Table(name = "employe")
@Data
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDate dateEmbauche;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "id_compagnie")
    private CompagnieAerienne compagnie;

    @OneToMany(mappedBy = "employe")
    private List<EmployePoste> postes;

    @OneToMany(mappedBy = "employe")
    private List<AffectationVol> affectations;
}

package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "compagnie_aerienne")
@Data
public class CompagnieAerienne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String pays;
    private String codeIata;
    private String codeIcao;
    private Boolean actif;

    @OneToMany(mappedBy = "compagnie")
    private List<Avion> avions;

    @OneToMany(mappedBy = "compagnie")
    private List<Vol> vols;

    @OneToMany(mappedBy = "compagnie")
    private List<Employe> employes;
}

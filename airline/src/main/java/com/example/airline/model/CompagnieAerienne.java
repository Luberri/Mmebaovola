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

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPays() {
        return pays;
    }

    public String getCodeIata() {
        return codeIata;
    }

    public String getCodeIcao() {
        return codeIcao;
    }

    public Boolean getActif() {
        return actif;
    }

    public List<Avion> getAvions() {
        return avions;
    }

    public List<Vol> getVols() {
        return vols;
    }

    public List<Employe> getEmployes() {
        return employes;
    }
}

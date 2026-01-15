package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "aeroport")
@Data
public class Aeroport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String ville;
    private String pays;
    private String codeIata;
    private String codeIcao;
    private Boolean actif;

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getVille() {
        return ville;
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
}

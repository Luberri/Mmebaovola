package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pub_contrat_facture")
@Data
public class PubContrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;
    private Integer nbrDiffusion;

    @ManyToOne
    @JoinColumn(name = "id_societe")
    private Societe societe;

    @ManyToOne
    @JoinColumn(name = "id_configuration_prix")
    private ConfigurationPrix configurationPrix;

    @OneToMany(mappedBy = "pubContrat")
    private List<DiffusionVol> diffusions;
}
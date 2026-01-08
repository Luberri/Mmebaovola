package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tarif_vol_classe")
@Data
public class TarifVolClasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double prixBase;
    private Integer nombreSieges;
    private Integer siegesRestants;

    @ManyToOne
    @JoinColumn(name = "id_vol")
    private Vol vol;

    @ManyToOne
    @JoinColumn(name = "id_classe")
    private ClasseVoyage classe;
}

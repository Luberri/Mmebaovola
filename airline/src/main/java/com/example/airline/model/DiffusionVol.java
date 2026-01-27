package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "diffusion_vol_facture")
@Data
public class DiffusionVol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nbrDiffusion;

    @ManyToOne
    @JoinColumn(name = "id_pub_contrat")
    private PubContrat pubContrat;

    @ManyToOne
    @JoinColumn(name = "id_vol")
    private Vol vol;
}

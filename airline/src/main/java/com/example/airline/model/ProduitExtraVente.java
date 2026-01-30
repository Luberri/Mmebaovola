package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "produit_extra_vente")
@Data
public class ProduitExtraVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private ProduitExtra produit;

    @ManyToOne
    @JoinColumn(name = "id_vol")
    private Vol vol;

    private Integer quantite;
}

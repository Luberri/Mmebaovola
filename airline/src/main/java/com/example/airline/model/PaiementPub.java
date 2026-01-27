package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiement_pub")
@Data
public class PaiementPub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal montant;
    private LocalDateTime datePaiement;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_diffusion_vol")
    private DiffusionVol diffusionVol;
}

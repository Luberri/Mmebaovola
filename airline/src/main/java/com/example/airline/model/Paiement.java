package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiement")
@Data
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;
    private String modePaiement;
    private LocalDateTime datePaiement;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;
}

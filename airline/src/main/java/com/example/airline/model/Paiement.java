package com.example.airline.model;

import com.example.airline.model.enums.ModePaiement;
import com.example.airline.model.enums.StatutPaiement;
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

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;

    private LocalDateTime datePaiement;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;

    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;
}

package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroSiege;
    private Double prixPaye;
    private LocalDateTime dateReservation;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "id_passager")
    private Passager passager;

    @ManyToOne
    @JoinColumn(name = "id_vol")
    private Vol vol;

    @ManyToOne
    @JoinColumn(name = "id_classe")
    private ClasseVoyage classe;
}

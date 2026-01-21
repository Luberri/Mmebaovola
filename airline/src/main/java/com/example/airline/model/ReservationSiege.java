package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reservation_siege")
@Data
public class ReservationSiege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numSiege;

    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;
}
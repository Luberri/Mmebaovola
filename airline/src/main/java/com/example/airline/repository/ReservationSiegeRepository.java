package com.example.airline.repository;

import com.example.airline.model.ReservationSiege;
import com.example.airline.model.Vol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSiegeRepository extends JpaRepository<ReservationSiege, Long> {
    boolean existsByNumSiegeAndReservation_Vol(String numSiege, Vol vol);
}
package com.example.airline.repository;

import com.example.airline.model.Reservation;
import com.example.airline.model.Vol;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByVol(Vol vol);
}
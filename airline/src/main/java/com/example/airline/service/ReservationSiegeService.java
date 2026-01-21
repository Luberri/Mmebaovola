package com.example.airline.service;

import com.example.airline.model.ReservationSiege;
import com.example.airline.model.Vol;
import com.example.airline.repository.ReservationSiegeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationSiegeService {
    private final ReservationSiegeRepository reservationSiegeRepository;

    public ReservationSiegeService(ReservationSiegeRepository reservationSiegeRepository) {
        this.reservationSiegeRepository = reservationSiegeRepository;
    }

    public boolean existsByNumSiegeAndVol(String numSiege, Vol vol) {
        return reservationSiegeRepository.existsByNumSiegeAndReservation_Vol(numSiege, vol);
    }

    public ReservationSiege save(ReservationSiege rs) {
        return reservationSiegeRepository.save(rs);
    }
}
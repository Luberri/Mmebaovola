package com.example.airline.service;

import com.example.airline.model.CapaciteAvionClasse;
import com.example.airline.model.Reservation;
import com.example.airline.model.TarifVolClasseType;
import com.example.airline.model.Vol;
import com.example.airline.repository.VolRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VolService {

    private final VolRepository volRepository;
    private final CapaciteAvionClasseService capaciteAvionClasseService;
    private final TarifVolClasseTypeService tarifVolClasseTypeService;
    private final ReservationService reservationService;

    public VolService(VolRepository volRepository, 
                      CapaciteAvionClasseService capaciteAvionClasseService,
                      TarifVolClasseTypeService tarifVolClasseTypeService,
                        ReservationService reservationService) {
        this.volRepository = volRepository;
        this.capaciteAvionClasseService = capaciteAvionClasseService;
        this.tarifVolClasseTypeService = tarifVolClasseTypeService;
        this.reservationService = reservationService;
    }

    public List<Vol> findAll() {
        return volRepository.findAll();
    }

    public Optional<Vol> findById(Long id) {
        return volRepository.findById(id);
    }

    public Vol save(Vol vol) {
        return volRepository.save(vol);
    }

    public void deleteById(Long id) {
        volRepository.deleteById(id);
    }

public BigDecimal calculateMaxRevenueForVol(Vol vol) {
    BigDecimal totalRevenue = BigDecimal.ZERO;

    // 1️⃣ Récupérer toutes les réservations déjà effectuées pour ce vol
    List<Reservation> reservations = reservationService.findByVol(vol);

    // 2️⃣ Calculer le revenu des réservations existantes
    BigDecimal reservationRevenue = BigDecimal.ZERO;
    for (Reservation reservation : reservations) {
        if (reservation.getTarifVolClasseType() != null &&
            reservation.getTarifVolClasseType().getPrix() != null) {
            reservationRevenue = reservationRevenue.add(reservation.getTarifVolClasseType().getPrix());
        }
    }

    // 3️⃣ Pour chaque classe, calculer le revenu potentiel avec le tarif type='N'
    List<CapaciteAvionClasse> capacites = capaciteAvionClasseService.findByVol(vol);
    for (CapaciteAvionClasse cap : capacites) {
        TarifVolClasseType tarifNormal = tarifVolClasseTypeService
            .findByVolAndClasseAndType(vol, cap.getClasse(), "N");
        if (tarifNormal != null && cap.getNbrPlace() > 0) {
            // Compter les sièges réservés sur cette classe
            long siegesReserves = reservations.stream()
                .filter(r -> r.getTarifVolClasseType() != null &&
                             r.getTarifVolClasseType().getClasse().equals(cap.getClasse()))
                .flatMap(r -> r.getReservationSieges().stream())
                .count();
            int placesRestantes = cap.getNbrPlace() - (int) siegesReserves;
            if (placesRestantes > 0) {
                BigDecimal revenueRestant = tarifNormal.getPrix()
                    .multiply(BigDecimal.valueOf(placesRestantes));
                totalRevenue = totalRevenue.add(revenueRestant);
            }
        }
    }

    // 4️⃣ Ajouter le revenu des réservations déjà effectuées
    totalRevenue = totalRevenue.add(reservationRevenue);

    return totalRevenue;
}

}
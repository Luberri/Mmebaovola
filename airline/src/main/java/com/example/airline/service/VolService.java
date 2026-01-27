package com.example.airline.service;

import com.example.airline.model.CapaciteAvionClasse;
import com.example.airline.model.DiffusionVol;
import com.example.airline.model.Reservation;
import com.example.airline.model.TarifVolClasseType;
import com.example.airline.model.Vol;
import com.example.airline.repository.VolRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VolService {

    private final VolRepository volRepository;
    private final CapaciteAvionClasseService capaciteAvionClasseService;
    private final TarifVolClasseTypeService tarifVolClasseTypeService;
    private final ReservationService reservationService;
    private final DiffusionVolService diffusionVolService;
    private final PaiementPubService paiementPubService;

    public VolService(VolRepository volRepository, 
                      CapaciteAvionClasseService capaciteAvionClasseService,
                      TarifVolClasseTypeService tarifVolClasseTypeService,
                      ReservationService reservationService,
                      DiffusionVolService diffusionVolService,
                      PaiementPubService paiementPubService) {
        this.volRepository = volRepository;
        this.capaciteAvionClasseService = capaciteAvionClasseService;
        this.tarifVolClasseTypeService = tarifVolClasseTypeService;
        this.reservationService = reservationService;
        this.diffusionVolService = diffusionVolService;
        this.paiementPubService = paiementPubService;
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

    /**
     * Calcule le montant estimé des diffusions publicitaires pour un vol
     * (montant total à payer basé sur nbrDiffusion * prix unitaire)
     */
    public BigDecimal calculateEstimatedPubRevenueForVol(Vol vol) {
        List<DiffusionVol> diffusions = diffusionVolService.findByVol(vol);
        BigDecimal total = BigDecimal.ZERO;
        
        for (DiffusionVol diffusion : diffusions) {
            total = total.add(paiementPubService.getMontantTotal(diffusion));
        }
        
        return total;
    }

    /**
     * Calcule le montant réellement payé des diffusions publicitaires pour un vol
     */
    public BigDecimal calculateActualPubRevenueForVol(Vol vol) {
        List<DiffusionVol> diffusions = diffusionVolService.findByVol(vol);
        BigDecimal total = BigDecimal.ZERO;
        
        for (DiffusionVol diffusion : diffusions) {
            total = total.add(paiementPubService.getTotalPaye(diffusion));
        }
        
        return total;
    }

    /**
     * Calcule le reste à payer des diffusions publicitaires pour un vol
     */
    public BigDecimal calculateRemainingPubRevenueForVol(Vol vol) {
        List<DiffusionVol> diffusions = diffusionVolService.findByVol(vol);
        BigDecimal total = BigDecimal.ZERO;
        
        for (DiffusionVol diffusion : diffusions) {
            total = total.add(paiementPubService.getResteAPayer(diffusion));
        }
        
        return total;
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

    /**
     * Compte le nombre de places vendues (sièges réservés) pour un vol
     */
    public int countPlacesVendues(Vol vol) {
        List<Reservation> reservations = reservationService.findByVol(vol);
        return reservations.stream()
                .filter(r -> r.getReservationSieges() != null)
                .mapToInt(r -> r.getReservationSieges().size())
                .sum();
    }

    /**
     * Récupère la capacité totale d'un vol (somme des places de toutes les classes)
     */
    public int getCapaciteTotale(Vol vol) {
        List<CapaciteAvionClasse> capacites = capaciteAvionClasseService.findByVol(vol);
        return capacites.stream()
                .filter(c -> c.getNbrPlace() != null)
                .mapToInt(CapaciteAvionClasse::getNbrPlace)
                .sum();
    }

    /**
     * Calcule le revenu total des réservations pour un vol
     * (somme des prix des billets vendus)
     */
    public BigDecimal calculateReservationRevenue(Vol vol) {
        List<Reservation> reservations = reservationService.findByVol(vol);
        BigDecimal total = BigDecimal.ZERO;
        
        for (Reservation reservation : reservations) {
            if (reservation.getTarifVolClasseType() != null &&
                reservation.getTarifVolClasseType().getPrix() != null) {
                // Multiplier le prix par le nombre de sièges réservés
                int nbSieges = reservation.getReservationSieges() != null 
                        ? reservation.getReservationSieges().size() 
                        : 1;
                total = total.add(reservation.getTarifVolClasseType().getPrix()
                        .multiply(BigDecimal.valueOf(nbSieges)));
            }
        }
        
        return total;
    }

    /**
     * Trouve les vols entre deux dates
     */
    public List<Vol> findByDateRange(LocalDate dateDebut, LocalDate dateFin) {
        return volRepository.findByDateHeureDepartBetween(
            dateDebut.atStartOfDay(), 
            dateFin.atTime(23, 59, 59)
        );
    }

    /**
     * Trouve les vols à partir d'une date
     */
    public List<Vol> findByDateFrom(LocalDate dateDebut) {
        return volRepository.findByDateHeureDepartGreaterThanEqual(dateDebut.atStartOfDay());
    }

    /**
     * Trouve les vols jusqu'à une date
     */
    public List<Vol> findByDateTo(LocalDate dateFin) {
        return volRepository.findByDateHeureDepartLessThanEqual(dateFin.atTime(23, 59, 59));
    }
}
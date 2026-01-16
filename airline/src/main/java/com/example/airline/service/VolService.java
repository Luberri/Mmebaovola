package com.example.airline.service;

import com.example.airline.model.CapaciteAvionClasse;
import com.example.airline.model.TarifVolClasse;
import com.example.airline.model.Vol;
import com.example.airline.repository.VolRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VolService {

    private final VolRepository volRepository;
    private final TarifVolClasseService tarifVolClasseService;
    private final CapaciteAvionClasseService capaciteAvionClasseService;

    public VolService(VolRepository volRepository, TarifVolClasseService tarifVolClasseService,
                      CapaciteAvionClasseService capaciteAvionClasseService) {
        this.volRepository = volRepository;
        this.tarifVolClasseService = tarifVolClasseService;
        this.capaciteAvionClasseService = capaciteAvionClasseService;
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
        BigDecimal maxRevenue = BigDecimal.ZERO;

        // Récupérer les capacités des classes pour l'avion du vol
        List<CapaciteAvionClasse> capacites = capaciteAvionClasseService.findByAvion(vol.getAvion());

        for (CapaciteAvionClasse capacite : capacites) {
            // Récupérer le tarif pour chaque classe
            Optional<TarifVolClasse> tarif = tarifVolClasseService.findByVolAndClasse(vol, capacite.getClasse());
            if (tarif.isPresent()) {
                // Calculer le revenu pour cette classe
                BigDecimal revenueForClass = tarif.get().getPrix().multiply(BigDecimal.valueOf(capacite.getNbrPlace()));
                maxRevenue = maxRevenue.add(revenueForClass);
            }
        }

        return maxRevenue;
    }
}
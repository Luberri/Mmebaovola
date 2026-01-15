package com.example.airline.service;

import com.example.airline.model.CapaciteAvionClasse;
import com.example.airline.model.TarifVolClasse;
import com.example.airline.model.Vol;
import com.example.airline.model.ClasseVoyage;
import com.example.airline.repository.CapaciteAvionClasseRepository;
import com.example.airline.repository.TarifVolClasseRepository;
import com.example.airline.repository.VolRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VolService {

    private final VolRepository volRepository;
    private final TarifVolClasseRepository tarifVolClasseRepository;
    private final CapaciteAvionClasseRepository capaciteAvionClasseRepository;

    public VolService(VolRepository volRepository, TarifVolClasseRepository tarifVolClasseRepository,
                      CapaciteAvionClasseRepository capaciteAvionClasseRepository) {
        this.volRepository = volRepository;
        this.tarifVolClasseRepository = tarifVolClasseRepository;
        this.capaciteAvionClasseRepository = capaciteAvionClasseRepository;
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

    public BigDecimal calculateMaxRevenueForVol(Long volId) {
        // // Récupérer les tarifs associés au vol
        // List<TarifVolClasse> tarifs = tarifVolClasseRepository.findByVolId(volId);

        // if (tarifs == null || tarifs.isEmpty()) {
        //     System.out.println("Aucun tarif trouvé pour le vol ID: " + volId);
        //     return BigDecimal.ZERO;
        // }

        // // Calculer la valeur maximale en multipliant le prix par les places disponibles pour chaque tarif
        // BigDecimal totalRevenue = tarifs.stream()
        //     .map(tarif -> {
        //         BigDecimal prix = tarif.getPrix() != null ? tarif.getPrix() : BigDecimal.ZERO;
        //         long places = tarif.getPlacesDisponibles();
        //         return prix.multiply(BigDecimal.valueOf(places));
        //     })
        //     .reduce(BigDecimal.ZERO, BigDecimal::add);

        // System.out.println("Revenu total pour le vol ID " + volId + ": " + totalRevenue);
        return BigDecimal.valueOf(100000);
    }
}
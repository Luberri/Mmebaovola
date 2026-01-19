package com.example.airline.service;

import com.example.airline.model.ClasseVoyage;
import com.example.airline.model.TarifVolClasseType;
import com.example.airline.model.Vol;
import com.example.airline.repository.TarifVolClasseTypeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TarifVolClasseTypeService {

    private final TarifVolClasseTypeRepository tarifVolClasseTypeRepository;

    public TarifVolClasseTypeService(TarifVolClasseTypeRepository tarifVolClasseTypeRepository) {
        this.tarifVolClasseTypeRepository = tarifVolClasseTypeRepository;
    }

    public List<TarifVolClasseType> findAll() {
        return tarifVolClasseTypeRepository.findAll();
    }

    public Optional<TarifVolClasseType> findById(Long id) {
        return tarifVolClasseTypeRepository.findById(id);
    }

    public TarifVolClasseType save(TarifVolClasseType tarif) {
        // Si pourcentage et référence sont définis, calculer le prix automatiquement
        if (tarif.getPourcentage() != null && tarif.getTarifReference() != null) {
            BigDecimal prixCalcule = tarif.getTarifReference().getPrix()
                .multiply(tarif.getPourcentage())
                .divide(BigDecimal.valueOf(100));
            tarif.setPrix(prixCalcule);
        }
        return tarifVolClasseTypeRepository.save(tarif);
    }

    public void deleteById(Long id) {
        tarifVolClasseTypeRepository.deleteById(id);
    }

    public TarifVolClasseType findByVolAndClasseAndType(Vol vol, ClasseVoyage classe, String type) {
        return tarifVolClasseTypeRepository.findByVolAndClasseAndType(vol, classe, type);
    }

    public List<TarifVolClasseType> findByVol(Vol vol) {
        return tarifVolClasseTypeRepository.findByVol(vol);
    }
}
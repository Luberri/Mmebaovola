package com.example.airline.service;

import com.example.airline.model.Avion;
import com.example.airline.model.CapaciteAvionClasse;
import com.example.airline.repository.CapaciteAvionClasseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapaciteAvionClasseService {

    private final CapaciteAvionClasseRepository capaciteAvionClasseRepository;

    public CapaciteAvionClasseService(CapaciteAvionClasseRepository capaciteAvionClasseRepository) {
        this.capaciteAvionClasseRepository = capaciteAvionClasseRepository;
    }

    public List<CapaciteAvionClasse> findAll() {
        return capaciteAvionClasseRepository.findAll();
    }

    public Optional<CapaciteAvionClasse> findById(Long id) {
        return capaciteAvionClasseRepository.findById(id);
    }

    public CapaciteAvionClasse save(CapaciteAvionClasse capaciteAvionClasse) {
        return capaciteAvionClasseRepository.save(capaciteAvionClasse);
    }

    public void deleteById(Long id) {
        capaciteAvionClasseRepository.deleteById(id);
    }

    public List<CapaciteAvionClasse> findByAvion(Avion avion) {
        return capaciteAvionClasseRepository.findByAvion(avion);
    }
}
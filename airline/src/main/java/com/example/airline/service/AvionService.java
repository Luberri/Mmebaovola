package com.example.airline.service;

import com.example.airline.model.Avion;
import com.example.airline.repository.AvionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvionService {

    private final AvionRepository avionRepository;

    public AvionService(AvionRepository avionRepository) {
        this.avionRepository = avionRepository;
    }

    public List<Avion> findAll() {
        return avionRepository.findAll();
    }

    public Optional<Avion> findById(Long id) {
        return avionRepository.findById(id);
    }

    public Avion save(Avion avion) {
        return avionRepository.save(avion);
    }

    public void deleteById(Long id) {
        avionRepository.deleteById(id);
    }
}
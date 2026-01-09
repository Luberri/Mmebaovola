package com.example.airline.service;

import com.example.airline.model.Aeroport;
import com.example.airline.repository.AeroportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AeroportService {

    private final AeroportRepository aeroportRepository;

    public AeroportService(AeroportRepository aeroportRepository) {
        this.aeroportRepository = aeroportRepository;
    }

    public List<Aeroport> findAll() {
        return aeroportRepository.findAll();
    }

    public Optional<Aeroport> findById(Long id) {
        return aeroportRepository.findById(id);
    }

    public Aeroport save(Aeroport aeroport) {
        return aeroportRepository.save(aeroport);
    }

    public void deleteById(Long id) {
        aeroportRepository.deleteById(id);
    }
}
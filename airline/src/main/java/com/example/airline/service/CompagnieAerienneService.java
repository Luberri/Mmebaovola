package com.example.airline.service;

import com.example.airline.model.CompagnieAerienne;
import com.example.airline.repository.CompagnieAerienneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompagnieAerienneService {

    private final CompagnieAerienneRepository compagnieAerienneRepository;

    public CompagnieAerienneService(CompagnieAerienneRepository compagnieAerienneRepository) {
        this.compagnieAerienneRepository = compagnieAerienneRepository;
    }

    public List<CompagnieAerienne> findAll() {
        return compagnieAerienneRepository.findAll();
    }

    public Optional<CompagnieAerienne> findById(Long id) {
        return compagnieAerienneRepository.findById(id);
    }

    public CompagnieAerienne save(CompagnieAerienne compagnieAerienne) {
        return compagnieAerienneRepository.save(compagnieAerienne);
    }

    public void deleteById(Long id) {
        compagnieAerienneRepository.deleteById(id);
    }
}
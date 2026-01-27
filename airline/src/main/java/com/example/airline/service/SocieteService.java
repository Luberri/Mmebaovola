package com.example.airline.service;

import com.example.airline.model.Societe;
import com.example.airline.repository.SocieteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocieteService {

    private final SocieteRepository societeRepository;

    public SocieteService(SocieteRepository societeRepository) {
        this.societeRepository = societeRepository;
    }

    public List<Societe> findAll() {
        return societeRepository.findAll();
    }

    public Optional<Societe> findById(Long id) {
        return societeRepository.findById(id);
    }

    public Societe save(Societe societe) {
        return societeRepository.save(societe);
    }

    public void deleteById(Long id) {
        societeRepository.deleteById(id);
    }
}

package com.example.airline.service;

import com.example.airline.model.AffectationVol;
import com.example.airline.repository.AffectationVolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AffectationVolService {

    private final AffectationVolRepository affectationVolRepository;

    public AffectationVolService(AffectationVolRepository affectationVolRepository) {
        this.affectationVolRepository = affectationVolRepository;
    }

    public List<AffectationVol> findAll() {
        return affectationVolRepository.findAll();
    }

    public Optional<AffectationVol> findById(Long id) {
        return affectationVolRepository.findById(id);
    }

    public AffectationVol save(AffectationVol affectationVol) {
        return affectationVolRepository.save(affectationVol);
    }

    public void deleteById(Long id) {
        affectationVolRepository.deleteById(id);
    }
}
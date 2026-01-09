package com.example.airline.service;

import com.example.airline.model.TarifVolClasse;
import com.example.airline.repository.TarifVolClasseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifVolClasseService {

    private final TarifVolClasseRepository tarifVolClasseRepository;

    public TarifVolClasseService(TarifVolClasseRepository tarifVolClasseRepository) {
        this.tarifVolClasseRepository = tarifVolClasseRepository;
    }

    public List<TarifVolClasse> findAll() {
        return tarifVolClasseRepository.findAll();
    }

    public Optional<TarifVolClasse> findById(Long id) {
        return tarifVolClasseRepository.findById(id);
    }

    public TarifVolClasse save(TarifVolClasse tarifVolClasse) {
        return tarifVolClasseRepository.save(tarifVolClasse);
    }

    public void deleteById(Long id) {
        tarifVolClasseRepository.deleteById(id);
    }
}
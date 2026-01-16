package com.example.airline.service;

import com.example.airline.model.TypeTarif;
import com.example.airline.repository.TypeTarifRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeTarifService {

    private final TypeTarifRepository typeTarifRepository;

    public TypeTarifService(TypeTarifRepository typeTarifRepository) {
        this.typeTarifRepository = typeTarifRepository;
    }

    public List<TypeTarif> findAll() {
        return typeTarifRepository.findAll();
    }
}
package com.example.airline.service;

import com.example.airline.model.ClasseVoyage;
import com.example.airline.repository.ClasseVoyageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseVoyageService {

    private final ClasseVoyageRepository classeVoyageRepository;

    public ClasseVoyageService(ClasseVoyageRepository classeVoyageRepository) {
        this.classeVoyageRepository = classeVoyageRepository;
    }

    public List<ClasseVoyage> findAll() {
        return classeVoyageRepository.findAll();
    }

    public Optional<ClasseVoyage> findById(Long id) {
        return classeVoyageRepository.findById(id);
    }

    public ClasseVoyage save(ClasseVoyage classeVoyage) {
        return classeVoyageRepository.save(classeVoyage);
    }

    public void deleteById(Long id) {
        classeVoyageRepository.deleteById(id);
    }
}
package com.example.airline.service;

import com.example.airline.model.Passager;
import com.example.airline.repository.PassagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassagerService {

    private final PassagerRepository passagerRepository;

    public PassagerService(PassagerRepository passagerRepository) {
        this.passagerRepository = passagerRepository;
    }

    public List<Passager> findAll() {
        return passagerRepository.findAll();
    }

    public Optional<Passager> findById(Long id) {
        return passagerRepository.findById(id);
    }

    public Passager save(Passager passager) {
        return passagerRepository.save(passager);
    }

    public void deleteById(Long id) {
        passagerRepository.deleteById(id);
    }
}
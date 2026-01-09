package com.example.airline.service;

import com.example.airline.model.Vol;
import com.example.airline.repository.VolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolService {

    private final VolRepository volRepository;

    public VolService(VolRepository volRepository) {
        this.volRepository = volRepository;
    }

    public List<Vol> findAll() {
        return volRepository.findAll();
    }

    public Optional<Vol> findById(Long id) {
        return volRepository.findById(id);
    }

    public Vol save(Vol vol) {
        return volRepository.save(vol);
    }

    public void deleteById(Long id) {
        volRepository.deleteById(id);
    }
}
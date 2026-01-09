package com.example.airline.service;

import com.example.airline.model.EmployePoste;
import com.example.airline.repository.EmployePosteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployePosteService {

    private final EmployePosteRepository employePosteRepository;

    public EmployePosteService(EmployePosteRepository employePosteRepository) {
        this.employePosteRepository = employePosteRepository;
    }

    public List<EmployePoste> findAll() {
        return employePosteRepository.findAll();
    }

    public Optional<EmployePoste> findById(Long id) {
        return employePosteRepository.findById(id);
    }

    public EmployePoste save(EmployePoste employePoste) {
        return employePosteRepository.save(employePoste);
    }

    public void deleteById(Long id) {
        employePosteRepository.deleteById(id);
    }
}
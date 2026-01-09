package com.example.airline.repository;

import com.example.airline.model.Aeroport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AeroportRepository extends JpaRepository<Aeroport, Long> {
}
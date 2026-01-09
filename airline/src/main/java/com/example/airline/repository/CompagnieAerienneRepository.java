package com.example.airline.repository;

import com.example.airline.model.CompagnieAerienne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompagnieAerienneRepository extends JpaRepository<CompagnieAerienne, Long> {
}
package com.example.airline.repository;

import com.example.airline.model.Avion;
import com.example.airline.model.CapaciteAvionClasse;
import com.example.airline.model.Vol;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapaciteAvionClasseRepository extends JpaRepository<CapaciteAvionClasse, Long> {
    List<CapaciteAvionClasse> findByVol(Vol vol);
}
package com.example.airline.repository;

import com.example.airline.model.TarifVolClasse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarifVolClasseRepository extends JpaRepository<TarifVolClasse, Long> {
    List<TarifVolClasse> findByVolId(Long volId);
}
package com.example.airline.repository;

import com.example.airline.model.TarifVolClasse;
import com.example.airline.model.Vol;
import com.example.airline.model.ClasseVoyage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarifVolClasseRepository extends JpaRepository<TarifVolClasse, Long> {
    Optional<TarifVolClasse> findByVolAndClasseVoyage(Vol vol, ClasseVoyage classe);
}
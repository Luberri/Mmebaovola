package com.example.airline.repository;

import com.example.airline.model.PubContrat;
import com.example.airline.model.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface PubContratRepository extends JpaRepository<PubContrat, Long> {
    
    List<PubContrat> findBySociete(Societe societe);
    
    List<PubContrat> findByDateDebutBetween(LocalDate debut, LocalDate fin);
    
    List<PubContrat> findByDateFinAfter(LocalDate date);
}

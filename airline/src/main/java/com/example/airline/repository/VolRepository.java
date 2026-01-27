package com.example.airline.repository;

import com.example.airline.model.Vol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VolRepository extends JpaRepository<Vol, Long> {
    
    List<Vol> findByDateHeureDepartBetween(LocalDateTime debut, LocalDateTime fin);
    
    List<Vol> findByDateHeureDepartGreaterThanEqual(LocalDateTime debut);
    
    List<Vol> findByDateHeureDepartLessThanEqual(LocalDateTime fin);
}
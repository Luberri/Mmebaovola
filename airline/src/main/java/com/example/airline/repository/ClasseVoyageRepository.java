package com.example.airline.repository;

import com.example.airline.model.ClasseVoyage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClasseVoyageRepository extends JpaRepository<ClasseVoyage, Long> {
}
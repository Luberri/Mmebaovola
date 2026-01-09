package com.example.airline.repository;

import com.example.airline.model.EmployePoste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployePosteRepository extends JpaRepository<EmployePoste, Long> {
}
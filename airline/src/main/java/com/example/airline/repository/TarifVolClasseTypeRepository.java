package com.example.airline.repository;

import com.example.airline.model.ClasseVoyage;
import com.example.airline.model.TarifVolClasseType;
import com.example.airline.model.Vol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarifVolClasseTypeRepository extends JpaRepository<TarifVolClasseType, Long> {
    TarifVolClasseType findByVolAndClasseAndType(Vol vol, ClasseVoyage classe, String type);
    List<TarifVolClasseType> findByVol(Vol vol);
}
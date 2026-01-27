package com.example.airline.repository;

import com.example.airline.model.DiffusionVol;
import com.example.airline.model.PaiementPub;
import com.example.airline.model.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaiementPubRepository extends JpaRepository<PaiementPub, Long> {
    
    List<PaiementPub> findByDiffusionVol(DiffusionVol diffusionVol);
    
    List<PaiementPub> findByDiffusionVolPubContratSociete(Societe societe);
    
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM PaiementPub p WHERE p.diffusionVol = :diffusion")
    BigDecimal getTotalPaiementsByDiffusion(@Param("diffusion") DiffusionVol diffusion);
    
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM PaiementPub p WHERE p.diffusionVol.id = :diffusionId")
    BigDecimal getTotalPaiementsByDiffusionId(@Param("diffusionId") Long diffusionId);
}

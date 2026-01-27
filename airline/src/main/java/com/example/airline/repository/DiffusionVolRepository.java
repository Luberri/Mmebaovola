package com.example.airline.repository;

import com.example.airline.model.DiffusionVol;
import com.example.airline.model.PubContrat;
import com.example.airline.model.Societe;
import com.example.airline.model.Vol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DiffusionVolRepository extends JpaRepository<DiffusionVol, Long> {
    
    List<DiffusionVol> findByPubContrat(PubContrat pubContrat);
    
    List<DiffusionVol> findByVol(Vol vol);
    
    List<DiffusionVol> findByPubContratSociete(Societe societe);
    
    @Query("SELECT SUM(d.nbrDiffusion) FROM DiffusionVol d WHERE d.pubContrat = :contrat")
    Integer getTotalDiffusionsByContrat(@Param("contrat") PubContrat contrat);
    
    @Query("SELECT SUM(d.nbrDiffusion) FROM DiffusionVol d WHERE d.vol = :vol")
    Integer getTotalDiffusionsByVol(@Param("vol") Vol vol);
    
    // Filtre par date du vol (date réelle de diffusion)
    @Query("SELECT d FROM DiffusionVol d WHERE CAST(d.vol.dateHeureDepart AS LocalDate) BETWEEN :dateDebut AND :dateFin")
    List<DiffusionVol> findByContratDateRange(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);
}

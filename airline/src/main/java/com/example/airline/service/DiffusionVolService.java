package com.example.airline.service;

import com.example.airline.model.DiffusionVol;
import com.example.airline.model.PubContrat;
import com.example.airline.model.Societe;
import com.example.airline.model.Vol;
import com.example.airline.repository.DiffusionVolRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiffusionVolService {

    private final DiffusionVolRepository diffusionVolRepository;

    public DiffusionVolService(DiffusionVolRepository diffusionVolRepository) {
        this.diffusionVolRepository = diffusionVolRepository;
    }

    public List<DiffusionVol> findAll() {
        return diffusionVolRepository.findAll();
    }

    public Optional<DiffusionVol> findById(Long id) {
        return diffusionVolRepository.findById(id);
    }

    public DiffusionVol save(DiffusionVol diffusionVol) {
        return diffusionVolRepository.save(diffusionVol);
    }

    public void deleteById(Long id) {
        diffusionVolRepository.deleteById(id);
    }

    public List<DiffusionVol> findByPubContrat(PubContrat pubContrat) {
        return diffusionVolRepository.findByPubContrat(pubContrat);
    }

    public List<DiffusionVol> findByVol(Vol vol) {
        return diffusionVolRepository.findByVol(vol);
    }

    public List<DiffusionVol> findBySociete(Societe societe) {
        return diffusionVolRepository.findByPubContratSociete(societe);
    }

    public Integer getTotalDiffusionsByContrat(PubContrat pubContrat) {
        Integer total = diffusionVolRepository.getTotalDiffusionsByContrat(pubContrat);
        return total != null ? total : 0;
    }

    public Integer getTotalDiffusionsByVol(Vol vol) {
        Integer total = diffusionVolRepository.getTotalDiffusionsByVol(vol);
        return total != null ? total : 0;
    }

    /**
     * Calcule le chiffre d'affaires publicitaire entre deux dates
     * CA = Σ (nbrDiffusion * prix de configuration du contrat)
     */
    public BigDecimal calculateChiffreAffaires(LocalDate dateDebut, LocalDate dateFin) {
        List<DiffusionVol> diffusions = diffusionVolRepository.findByContratDateRange(dateDebut, dateFin);
        
        BigDecimal chiffreAffaires = BigDecimal.ZERO;
        for (DiffusionVol diffusion : diffusions) {
            if (diffusion.getPubContrat() != null 
                && diffusion.getPubContrat().getConfigurationPrix() != null
                && diffusion.getNbrDiffusion() != null) {
                
                BigDecimal prixUnitaire = diffusion.getPubContrat().getConfigurationPrix().getPrix();
                BigDecimal montant = prixUnitaire.multiply(BigDecimal.valueOf(diffusion.getNbrDiffusion()));
                chiffreAffaires = chiffreAffaires.add(montant);
            }
        }
        return chiffreAffaires;
    }

    /**
     * Récupère les diffusions dont le contrat est actif entre deux dates
     */
    public List<DiffusionVol> findByDateRange(LocalDate dateDebut, LocalDate dateFin) {
        return diffusionVolRepository.findByContratDateRange(dateDebut, dateFin);
    }
}

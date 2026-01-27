package com.example.airline.service;

import com.example.airline.model.DiffusionVol;
import com.example.airline.model.PaiementPub;
import com.example.airline.model.Societe;
import com.example.airline.repository.PaiementPubRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementPubService {

    private final PaiementPubRepository paiementPubRepository;

    public PaiementPubService(PaiementPubRepository paiementPubRepository) {
        this.paiementPubRepository = paiementPubRepository;
    }

    public List<PaiementPub> findAll() {
        return paiementPubRepository.findAll();
    }

    public Optional<PaiementPub> findById(Long id) {
        return paiementPubRepository.findById(id);
    }

    public PaiementPub save(PaiementPub paiementPub) {
        return paiementPubRepository.save(paiementPub);
    }

    public void deleteById(Long id) {
        paiementPubRepository.deleteById(id);
    }

    public List<PaiementPub> findByDiffusionVol(DiffusionVol diffusionVol) {
        return paiementPubRepository.findByDiffusionVol(diffusionVol);
    }

    /**
     * Trouve tous les paiements pour une société donnée
     */
    public List<PaiementPub> findBySociete(Societe societe) {
        return paiementPubRepository.findByDiffusionVolPubContratSociete(societe);
    }

    /**
     * Calcule le total déjà payé pour une diffusion
     */
    public BigDecimal getTotalPaye(DiffusionVol diffusionVol) {
        return paiementPubRepository.getTotalPaiementsByDiffusion(diffusionVol);
    }

    public BigDecimal getTotalPayeByDiffusionId(Long diffusionId) {
        return paiementPubRepository.getTotalPaiementsByDiffusionId(diffusionId);
    }

    /**
     * Calcule le montant total à payer pour une diffusion
     */
    public BigDecimal getMontantTotal(DiffusionVol diffusionVol) {
        if (diffusionVol.getPubContrat() != null 
            && diffusionVol.getPubContrat().getConfigurationPrix() != null
            && diffusionVol.getNbrDiffusion() != null) {
            
            BigDecimal prixUnitaire = diffusionVol.getPubContrat().getConfigurationPrix().getPrix();
            return prixUnitaire.multiply(BigDecimal.valueOf(diffusionVol.getNbrDiffusion()));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calcule le reste à payer pour une diffusion
     */
    public BigDecimal getResteAPayer(DiffusionVol diffusionVol) {
        BigDecimal montantTotal = getMontantTotal(diffusionVol);
        BigDecimal totalPaye = getTotalPaye(diffusionVol);
        return montantTotal.subtract(totalPaye);
    }

    /**
     * Vérifie si le paiement est complet
     */
    public boolean isPaiementComplet(DiffusionVol diffusionVol) {
        return getResteAPayer(diffusionVol).compareTo(BigDecimal.ZERO) <= 0;
    }
}

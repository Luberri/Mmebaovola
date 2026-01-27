package com.example.airline.service;

import com.example.airline.model.PubContrat;
import com.example.airline.model.Societe;
import com.example.airline.repository.PubContratRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PubContratService {

    private final PubContratRepository pubContratRepository;

    public PubContratService(PubContratRepository pubContratRepository) {
        this.pubContratRepository = pubContratRepository;
    }

    public List<PubContrat> findAll() {
        return pubContratRepository.findAll();
    }

    public Optional<PubContrat> findById(Long id) {
        return pubContratRepository.findById(id);
    }

    public PubContrat save(PubContrat pubContrat) {
        return pubContratRepository.save(pubContrat);
    }

    public void deleteById(Long id) {
        pubContratRepository.deleteById(id);
    }

    public List<PubContrat> findBySociete(Societe societe) {
        return pubContratRepository.findBySociete(societe);
    }

    public List<PubContrat> findContratsActifs() {
        return pubContratRepository.findByDateFinAfter(LocalDate.now());
    }

    /**
     * Calcule le montant total d'un contrat (prix * nombre de diffusions)
     */
    public BigDecimal calculerMontantTotal(PubContrat contrat) {
        if (contrat.getConfigurationPrix() != null && contrat.getNbrDiffusion() != null) {
            return contrat.getConfigurationPrix().getPrix()
                    .multiply(BigDecimal.valueOf(contrat.getNbrDiffusion()));
        }
        return BigDecimal.ZERO;
    }
}

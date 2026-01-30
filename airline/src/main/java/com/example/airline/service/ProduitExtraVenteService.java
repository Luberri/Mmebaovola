package com.example.airline.service;

import com.example.airline.model.ProduitExtra;
import com.example.airline.model.ProduitExtraVente;
import com.example.airline.model.Vol;
import com.example.airline.repository.ProduitExtraVenteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitExtraVenteService {

    private final ProduitExtraVenteRepository produitExtraVenteRepository;

    public ProduitExtraVenteService(ProduitExtraVenteRepository produitExtraVenteRepository) {
        this.produitExtraVenteRepository = produitExtraVenteRepository;
    }

    public List<ProduitExtraVente> findAll() {
        return produitExtraVenteRepository.findAll();
    }

    public Optional<ProduitExtraVente> findById(Long id) {
        return produitExtraVenteRepository.findById(id);
    }

    public ProduitExtraVente save(ProduitExtraVente produitExtraVente) {
        return produitExtraVenteRepository.save(produitExtraVente);
    }

    public void deleteById(Long id) {
        produitExtraVenteRepository.deleteById(id);
    }

    public List<ProduitExtraVente> findByVol(Vol vol) {
        return produitExtraVenteRepository.findByVol(vol);
    }

    public List<ProduitExtraVente> findByVolId(Long volId) {
        return produitExtraVenteRepository.findByVolId(volId);
    }

    public List<ProduitExtraVente> findByProduit(ProduitExtra produit) {
        return produitExtraVenteRepository.findByProduit(produit);
    }

    /**
     * Calcule le montant total d'une vente (prix * quantité)
     */
    public BigDecimal calculerMontantVente(ProduitExtraVente vente) {
        if (vente.getProduit() != null && vente.getQuantite() != null) {
            return vente.getProduit().getPrix()
                    .multiply(BigDecimal.valueOf(vente.getQuantite()));
        }
        return BigDecimal.ZERO;
    }
}

package com.example.airline.service;

import com.example.airline.model.ProduitExtra;
import com.example.airline.repository.ProduitExtraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitExtraService {

    private final ProduitExtraRepository produitExtraRepository;

    public ProduitExtraService(ProduitExtraRepository produitExtraRepository) {
        this.produitExtraRepository = produitExtraRepository;
    }

    public List<ProduitExtra> findAll() {
        return produitExtraRepository.findAll();
    }

    public Optional<ProduitExtra> findById(Long id) {
        return produitExtraRepository.findById(id);
    }

    public ProduitExtra save(ProduitExtra produitExtra) {
        return produitExtraRepository.save(produitExtra);
    }

    public void deleteById(Long id) {
        produitExtraRepository.deleteById(id);
    }

    public List<ProduitExtra> findByProduitContaining(String produit) {
        return produitExtraRepository.findByProduitContainingIgnoreCase(produit);
    }
}

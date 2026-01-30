package com.example.airline.repository;

import com.example.airline.model.ProduitExtra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitExtraRepository extends JpaRepository<ProduitExtra, Long> {

    List<ProduitExtra> findByProduitContainingIgnoreCase(String produit);
}

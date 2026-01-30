package com.example.airline.repository;

import com.example.airline.model.ProduitExtra;
import com.example.airline.model.ProduitExtraVente;
import com.example.airline.model.Vol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitExtraVenteRepository extends JpaRepository<ProduitExtraVente, Long> {

    List<ProduitExtraVente> findByVol(Vol vol);

    List<ProduitExtraVente> findByProduit(ProduitExtra produit);

    List<ProduitExtraVente> findByVolId(Long volId);
}

package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "tarif_vol_classe_type")
@Data
public class TarifVolClasseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal prix;
    private String type;
    
    // Nouveau: pourcentage (ex: 10 pour 10%)
    private BigDecimal pourcentage;
    
    // Nouveau: référence vers un autre tarif
    @ManyToOne
    @JoinColumn(name = "id_reference")
    private TarifVolClasseType tarifReference;

    @ManyToOne
    @JoinColumn(name = "id_vol")
    private Vol vol;

    @ManyToOne
    @JoinColumn(name = "id_classe")
    private ClasseVoyage classe;
    
    /**
     * Calcule le prix effectif:
     * - Si pourcentage et tarifReference sont définis, prix = tarifReference.prix * (pourcentage / 100)
     * - Sinon, retourne le prix directement saisi
     */
    public BigDecimal getPrixEffectif() {
        if (pourcentage != null && tarifReference != null && tarifReference.getPrix() != null) {
            return tarifReference.getPrix()
                .multiply(pourcentage)
                .divide(BigDecimal.valueOf(100));
        }
        return prix;
    }
}
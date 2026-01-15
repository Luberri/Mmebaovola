package com.example.airline.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

@Entity
@Table(name = "vol")
@Data
public class Vol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroVol;
    private LocalDateTime dateHeureDepart;
    private LocalDateTime dateHeureArrivee;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "id_compagnie")
    private CompagnieAerienne compagnie;

    @ManyToOne
    @JoinColumn(name = "id_avion")
    private Avion avion;

    @ManyToOne
    @JoinColumn(name = "id_aeroport_depart")
    private Aeroport aeroportDepart;

    @ManyToOne
    @JoinColumn(name = "id_aeroport_arrivee")
    private Aeroport aeroportArrivee;

    @OneToMany(mappedBy = "vol")
    private List<TarifVolClasse> tarifs;

    @OneToMany(mappedBy = "vol")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "vol")
    private List<AffectationVol> affectations;

    public Long getId() {
        return this.id;
    }
}

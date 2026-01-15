package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

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
        return id;
    }

    public String getNumeroVol() {
        return numeroVol;
    }

    public LocalDateTime getDateHeureDepart() {
        return dateHeureDepart;
    }

    public LocalDateTime getDateHeureArrivee() {
        return dateHeureArrivee;
    }

    public String getStatut() {
        return statut;
    }

    public CompagnieAerienne getCompagnie() {
        return compagnie;
    }

    public Avion getAvion() {
        return avion;
    }

    public Aeroport getAeroportDepart() {
        return aeroportDepart;
    }

    public Aeroport getAeroportArrivee() {
        return aeroportArrivee;
    }

    public List<TarifVolClasse> getTarifs() {
        return tarifs;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<AffectationVol> getAffectations() {
        return affectations;
    }
}

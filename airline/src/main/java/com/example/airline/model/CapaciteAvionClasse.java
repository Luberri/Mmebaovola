package com.example.airline.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "capacite_avion_classe")
@Data
public class CapaciteAvionClasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_avion", nullable = false)
    private Avion avion;

    @ManyToOne
    @JoinColumn(name = "id_classe", nullable = false)
    private ClasseVoyage classe;

    private Integer nbrSieges;

    public ClasseVoyage getClasse() {
        return classe;
    }

    public Integer getNbrSieges() {
        return nbrSieges;
    }
}
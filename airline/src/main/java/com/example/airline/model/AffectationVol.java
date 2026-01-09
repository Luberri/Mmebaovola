package com.example.airline.model;

import com.example.airline.model.enums.RoleVol;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "affectation_vol")
@Data
public class AffectationVol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleVol roleVol;

    @ManyToOne
    @JoinColumn(name = "id_vol")
    private Vol vol;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employe employe;
}

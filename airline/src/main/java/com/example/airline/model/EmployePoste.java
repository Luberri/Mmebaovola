package com.example.airline.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employe_poste")
@Data
public class EmployePoste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name = "id_poste")
    private Poste poste;
}
    

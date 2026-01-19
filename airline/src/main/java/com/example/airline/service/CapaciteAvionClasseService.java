package com.example.airline.service;

import com.example.airline.model.Avion;
import com.example.airline.model.CapaciteAvionClasse;
import com.example.airline.model.ClasseVoyage;
import com.example.airline.model.Vol;
import com.example.airline.repository.CapaciteAvionClasseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapaciteAvionClasseService {

    private final CapaciteAvionClasseRepository capaciteAvionClasseRepository;

    public CapaciteAvionClasseService(CapaciteAvionClasseRepository capaciteAvionClasseRepository) {
        this.capaciteAvionClasseRepository = capaciteAvionClasseRepository;
    }
       public CapaciteAvionClasse findByVolAndClasse(Vol vol, ClasseVoyage classe) {
           List<CapaciteAvionClasse> list = capaciteAvionClasseRepository.findByVol(vol);
           if (list != null) {
               for (CapaciteAvionClasse cap : list) {
                   if (cap.getClasse().equals(classe)) {
                       return cap;
                   }
               }
           }
           return null;
       }

    public List<CapaciteAvionClasse> findAll() {
        return capaciteAvionClasseRepository.findAll();
    }

    public Optional<CapaciteAvionClasse> findById(Long id) {
        return capaciteAvionClasseRepository.findById(id);
    }

    public CapaciteAvionClasse save(CapaciteAvionClasse capaciteAvionClasse) {
        return capaciteAvionClasseRepository.save(capaciteAvionClasse);
    }

    public void deleteById(Long id) {
        capaciteAvionClasseRepository.deleteById(id);
    }

    public List<CapaciteAvionClasse> findByVol(Vol vol) {
        return capaciteAvionClasseRepository.findByVol(vol);
    }
}
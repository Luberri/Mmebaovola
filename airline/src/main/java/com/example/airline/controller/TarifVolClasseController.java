package com.example.airline.controller;

import com.example.airline.model.TarifVolClasse;
import com.example.airline.service.TarifVolClasseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifs-vol-classe")
public class TarifVolClasseController {

    private final TarifVolClasseService tarifVolClasseService;

    public TarifVolClasseController(TarifVolClasseService tarifVolClasseService) {
        this.tarifVolClasseService = tarifVolClasseService;
    }

    @GetMapping
    public List<TarifVolClasse> getAllTarifsVolClasse() {
        return tarifVolClasseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifVolClasse> getTarifVolClasseById(@PathVariable Long id) {
        return tarifVolClasseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TarifVolClasse createTarifVolClasse(@RequestBody TarifVolClasse tarifVolClasse) {
        return tarifVolClasseService.save(tarifVolClasse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifVolClasse> updateTarifVolClasse(@PathVariable Long id, @RequestBody TarifVolClasse tarifVolClasse) {
        return tarifVolClasseService.findById(id)
                .map(existing -> {
                    tarifVolClasse.setId(id);
                    return ResponseEntity.ok(tarifVolClasseService.save(tarifVolClasse));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifVolClasse(@PathVariable Long id) {
        if (tarifVolClasseService.findById(id).isPresent()) {
            tarifVolClasseService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
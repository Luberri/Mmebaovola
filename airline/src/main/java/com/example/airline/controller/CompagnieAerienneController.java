package com.example.airline.controller;

import com.example.airline.model.CompagnieAerienne;
import com.example.airline.service.CompagnieAerienneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compagnies-aeriennes")
public class CompagnieAerienneController {

    private final CompagnieAerienneService compagnieAerienneService;

    public CompagnieAerienneController(CompagnieAerienneService compagnieAerienneService) {
        this.compagnieAerienneService = compagnieAerienneService;
    }

    @GetMapping
    public List<CompagnieAerienne> getAllCompagniesAeriennes() {
        return compagnieAerienneService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompagnieAerienne> getCompagnieAerienneById(@PathVariable Long id) {
        return compagnieAerienneService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CompagnieAerienne createCompagnieAerienne(@RequestBody CompagnieAerienne compagnieAerienne) {
        return compagnieAerienneService.save(compagnieAerienne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompagnieAerienne> updateCompagnieAerienne(@PathVariable Long id, @RequestBody CompagnieAerienne compagnieAerienne) {
        return compagnieAerienneService.findById(id)
                .map(existing -> {
                    compagnieAerienne.setId(id);
                    return ResponseEntity.ok(compagnieAerienneService.save(compagnieAerienne));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompagnieAerienne(@PathVariable Long id) {
        if (compagnieAerienneService.findById(id).isPresent()) {
            compagnieAerienneService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
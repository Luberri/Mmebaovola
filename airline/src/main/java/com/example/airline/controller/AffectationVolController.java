package com.example.airline.controller;

import com.example.airline.model.AffectationVol;
import com.example.airline.service.AffectationVolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affectations-vol")
public class AffectationVolController {

    private final AffectationVolService affectationVolService;

    public AffectationVolController(AffectationVolService affectationVolService) {
        this.affectationVolService = affectationVolService;
    }

    @GetMapping
    public List<AffectationVol> getAllAffectationsVol() {
        return affectationVolService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AffectationVol> getAffectationVolById(@PathVariable Long id) {
        return affectationVolService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AffectationVol createAffectationVol(@RequestBody AffectationVol affectationVol) {
        return affectationVolService.save(affectationVol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AffectationVol> updateAffectationVol(@PathVariable Long id, @RequestBody AffectationVol affectationVol) {
        return affectationVolService.findById(id)
                .map(existing -> {
                    affectationVol.setId(id);
                    return ResponseEntity.ok(affectationVolService.save(affectationVol));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAffectationVol(@PathVariable Long id) {
        if (affectationVolService.findById(id).isPresent()) {
            affectationVolService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
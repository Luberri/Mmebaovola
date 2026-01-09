package com.example.airline.controller;

import com.example.airline.model.Avion;
import com.example.airline.service.AvionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avions")
public class AvionController {

    private final AvionService avionService;

    public AvionController(AvionService avionService) {
        this.avionService = avionService;
    }

    @GetMapping
    public List<Avion> getAllAvions() {
        return avionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avion> getAvionById(@PathVariable Long id) {
        return avionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Avion createAvion(@RequestBody Avion avion) {
        return avionService.save(avion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avion> updateAvion(@PathVariable Long id, @RequestBody Avion avion) {
        return avionService.findById(id)
                .map(existing -> {
                    avion.setId(id);
                    return ResponseEntity.ok(avionService.save(avion));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvion(@PathVariable Long id) {
        if (avionService.findById(id).isPresent()) {
            avionService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
package com.example.airline.controller;

import com.example.airline.model.Aeroport;
import com.example.airline.service.AeroportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//hhhh
@RestController
@RequestMapping("/api/aeroports")
public class AeroportController {

    private final AeroportService aeroportService;

    public AeroportController(AeroportService aeroportService) {
        this.aeroportService = aeroportService;
    }

    @GetMapping
    public List<Aeroport> getAllAeroports() {
        return aeroportService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aeroport> getAeroportById(@PathVariable Long id) {
        return aeroportService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aeroport createAeroport(@RequestBody Aeroport aeroport) {
        return aeroportService.save(aeroport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aeroport> updateAeroport(@PathVariable Long id, @RequestBody Aeroport aeroport) {
        return aeroportService.findById(id)
                .map(existing -> {
                    aeroport.setId(id);
                    return ResponseEntity.ok(aeroportService.save(aeroport));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAeroport(@PathVariable Long id) {
        if (aeroportService.findById(id).isPresent()) {
            aeroportService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
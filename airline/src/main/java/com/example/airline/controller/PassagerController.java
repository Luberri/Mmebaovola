package com.example.airline.controller;

import com.example.airline.model.Passager;
import com.example.airline.service.PassagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passagers")
public class PassagerController {

    private final PassagerService passagerService;

    public PassagerController(PassagerService passagerService) {
        this.passagerService = passagerService;
    }

    @GetMapping
    public List<Passager> getAllPassagers() {
        return passagerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passager> getPassagerById(@PathVariable Long id) {
        return passagerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Passager createPassager(@RequestBody Passager passager) {
        return passagerService.save(passager);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passager> updatePassager(@PathVariable Long id, @RequestBody Passager passager) {
        return passagerService.findById(id)
                .map(existing -> {
                    passager.setId(id);
                    return ResponseEntity.ok(passagerService.save(passager));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassager(@PathVariable Long id) {
        if (passagerService.findById(id).isPresent()) {
            passagerService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
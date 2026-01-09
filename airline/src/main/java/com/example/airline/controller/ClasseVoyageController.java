package com.example.airline.controller;

import com.example.airline.model.ClasseVoyage;
import com.example.airline.service.ClasseVoyageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes-voyage")
public class ClasseVoyageController {

    private final ClasseVoyageService classeVoyageService;

    public ClasseVoyageController(ClasseVoyageService classeVoyageService) {
        this.classeVoyageService = classeVoyageService;
    }

    @GetMapping
    public List<ClasseVoyage> getAllClassesVoyage() {
        return classeVoyageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseVoyage> getClasseVoyageById(@PathVariable Long id) {
        return classeVoyageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClasseVoyage createClasseVoyage(@RequestBody ClasseVoyage classeVoyage) {
        return classeVoyageService.save(classeVoyage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClasseVoyage> updateClasseVoyage(@PathVariable Long id, @RequestBody ClasseVoyage classeVoyage) {
        return classeVoyageService.findById(id)
                .map(existing -> {
                    classeVoyage.setId(id);
                    return ResponseEntity.ok(classeVoyageService.save(classeVoyage));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasseVoyage(@PathVariable Long id) {
        if (classeVoyageService.findById(id).isPresent()) {
            classeVoyageService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
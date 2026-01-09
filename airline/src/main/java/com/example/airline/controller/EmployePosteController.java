package com.example.airline.controller;

import com.example.airline.model.EmployePoste;
import com.example.airline.service.EmployePosteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employe-postes")
public class EmployePosteController {

    private final EmployePosteService employePosteService;

    public EmployePosteController(EmployePosteService employePosteService) {
        this.employePosteService = employePosteService;
    }

    @GetMapping
    public List<EmployePoste> getAllEmployePostes() {
        return employePosteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployePoste> getEmployePosteById(@PathVariable Long id) {
        return employePosteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployePoste createEmployePoste(@RequestBody EmployePoste employePoste) {
        return employePosteService.save(employePoste);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployePoste> updateEmployePoste(@PathVariable Long id, @RequestBody EmployePoste employePoste) {
        return employePosteService.findById(id)
                .map(existing -> {
                    employePoste.setId(id);
                    return ResponseEntity.ok(employePosteService.save(employePoste));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployePoste(@PathVariable Long id) {
        if (employePosteService.findById(id).isPresent()) {
            employePosteService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
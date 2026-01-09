package com.example.airline.controller;

import com.example.airline.model.Vol;
import com.example.airline.service.VolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vols")
public class VolController {

    private final VolService volService;

    public VolController(VolService volService) {
        this.volService = volService;
    }

    @GetMapping
    public List<Vol> getAllVols() {
        return volService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vol> getVolById(@PathVariable Long id) {
        return volService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Vol createVol(@RequestBody Vol vol) {
        return volService.save(vol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vol> updateVol(@PathVariable Long id, @RequestBody Vol vol) {
        return volService.findById(id)
                .map(existing -> {
                    vol.setId(id);
                    return ResponseEntity.ok(volService.save(vol));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVol(@PathVariable Long id) {
        if (volService.findById(id).isPresent()) {
            volService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
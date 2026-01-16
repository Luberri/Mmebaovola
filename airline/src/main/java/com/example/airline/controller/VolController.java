// filepath: src/main/java/com/example/airline/controller/VolController.java
package com.example.airline.controller;

import com.example.airline.model.Vol;
import com.example.airline.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vols")
public class VolController {

    private final VolService volService;
    private final CompagnieAerienneService compagnieService;
    private final AvionService avionService;
    private final AeroportService aeroportService;

    public VolController(VolService volService, CompagnieAerienneService compagnieService,
                         AvionService avionService, AeroportService aeroportService) {
        this.volService = volService;
        this.compagnieService = compagnieService;
        this.avionService = avionService;
        this.aeroportService = aeroportService;
    }

    @GetMapping
    public String list(Model model) {
        List<Vol> vols = volService.findAll();
        Map<Long, BigDecimal> maxRevenues = vols.stream()
                .collect(Collectors.toMap(Vol::getId, volService::calculateMaxRevenueForVol));
        model.addAttribute("vols", vols);
        model.addAttribute("maxRevenues", maxRevenues);
        // The template 'vols/list' now uses Nav.html as layout
        return "vols/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("vol", new Vol());
        model.addAttribute("compagnies", compagnieService.findAll());
        model.addAttribute("avions", avionService.findAll());
        model.addAttribute("aeroports", aeroportService.findAll());
        return "vols/form";
    }

    @PostMapping
    public String save(@ModelAttribute Vol vol) {
        volService.save(vol);
        return "redirect:/vols";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        volService.findById(id).ifPresent(vol -> model.addAttribute("vol", vol));
        model.addAttribute("compagnies", compagnieService.findAll());
        model.addAttribute("avions", avionService.findAll());
        model.addAttribute("aeroports", aeroportService.findAll());
        return "vols/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        volService.deleteById(id);
        return "redirect:/vols";
    }

    @GetMapping("/{id}/revenue")
    public String showMaxRevenue(@PathVariable Long id, Model model) {
        Vol vol = volService.findById(id).orElseThrow(() -> new IllegalArgumentException("Vol introuvable avec l'ID : " + id));
        BigDecimal maxRevenue = volService.calculateMaxRevenueForVol(vol);
        model.addAttribute("vol", vol);
        model.addAttribute("maxRevenue", maxRevenue);
        return "vols/revenue";
    }
}
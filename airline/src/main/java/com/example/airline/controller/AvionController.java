// filepath: src/main/java/com/example/airline/controller/AvionController.java
package com.example.airline.controller;

import com.example.airline.model.Avion;
import com.example.airline.service.AvionService;
import com.example.airline.service.CompagnieAerienneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/avions")
public class AvionController {

    private final AvionService avionService;
    private final CompagnieAerienneService compagnieService;

    public AvionController(AvionService avionService, CompagnieAerienneService compagnieService) {
        this.avionService = avionService;
        this.compagnieService = compagnieService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("avions", avionService.findAll());
        return "avions/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("avion", new Avion());
        model.addAttribute("compagnies", compagnieService.findAll());
        return "avions/form";
    }

    @PostMapping
    public String save(@ModelAttribute Avion avion) {
        avionService.save(avion);
        return "redirect:/avions";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        avionService.findById(id).ifPresent(avion -> model.addAttribute("avion", avion));
        model.addAttribute("compagnies", compagnieService.findAll());
        return "avions/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        avionService.deleteById(id);
        return "redirect:/avions";
    }
}
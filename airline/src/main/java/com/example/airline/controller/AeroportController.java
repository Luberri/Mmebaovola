// filepath: src/main/java/com/example/airline/controller/AeroportController.java
package com.example.airline.controller;

import com.example.airline.model.Aeroport;
import com.example.airline.service.AeroportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/aeroports")
public class AeroportController {

    private final AeroportService aeroportService;

    public AeroportController(AeroportService aeroportService) {
        this.aeroportService = aeroportService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("aeroports", aeroportService.findAll());
        return "aeroports/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("aeroport", new Aeroport());
        return "aeroports/form";
    }

    @PostMapping
    public String save(@ModelAttribute Aeroport aeroport) {
        aeroportService.save(aeroport);
        return "redirect:/aeroports";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        aeroportService.findById(id).ifPresent(aeroport -> model.addAttribute("aeroport", aeroport));
        return "aeroports/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        aeroportService.deleteById(id);
        return "redirect:/aeroports";
    }
}
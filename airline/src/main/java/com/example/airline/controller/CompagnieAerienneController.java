package com.example.airline.controller;

import com.example.airline.model.CompagnieAerienne;
import com.example.airline.service.CompagnieAerienneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/compagnies-aeriennes")
public class CompagnieAerienneController {

    private final CompagnieAerienneService compagnieAerienneService;

    public CompagnieAerienneController(CompagnieAerienneService compagnieAerienneService) {
        this.compagnieAerienneService = compagnieAerienneService;
    }

    @GetMapping
    public String listCompagnies(Model model) {
        model.addAttribute("compagnies", compagnieAerienneService.findAll());
        return "compagnies/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("compagnie", new CompagnieAerienne());
        return "compagnies/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return compagnieAerienneService.findById(id)
                .map(compagnie -> {
                    model.addAttribute("compagnie", compagnie);
                    return "compagnies/form";
                })
                .orElse("redirect:/compagnies-aeriennes");
    }

    @PostMapping("/save")
    public String saveCompagnie(@ModelAttribute CompagnieAerienne compagnie) {
        compagnieAerienneService.save(compagnie);
        return "redirect:/compagnies-aeriennes";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompagnie(@PathVariable Long id) {
        compagnieAerienneService.deleteById(id);
        return "redirect:/compagnies-aeriennes";
    }
}
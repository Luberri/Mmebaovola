package com.example.airline.controller;

import com.example.airline.model.Societe;
import com.example.airline.service.SocieteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/societes")
public class SocieteController {

    private final SocieteService societeService;

    public SocieteController(SocieteService societeService) {
        this.societeService = societeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("societes", societeService.findAll());
        return "societes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("societe", new Societe());
        return "societes/form";
    }

    @PostMapping
    public String save(@ModelAttribute Societe societe) {
        societeService.save(societe);
        return "redirect:/societes";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        societeService.findById(id).ifPresent(societe -> model.addAttribute("societe", societe));
        return "societes/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        societeService.deleteById(id);
        return "redirect:/societes";
    }
}

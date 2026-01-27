package com.example.airline.controller;

import com.example.airline.service.PubContratService;
import com.example.airline.service.SocieteService;
import com.example.airline.model.PubContrat;
import com.example.airline.service.ConfigurationPrixService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pub-contrats")
public class PubContratController {

    private final PubContratService pubContratService;
    private final SocieteService societeService;
    private final ConfigurationPrixService configurationPrixService;

    public PubContratController(PubContratService pubContratService,
                                 SocieteService societeService,
                                 ConfigurationPrixService configurationPrixService) {
        this.pubContratService = pubContratService;
        this.societeService = societeService;
        this.configurationPrixService = configurationPrixService;
    }

    @GetMapping
    public String list(Model model) {
        List<PubContrat> contrats = pubContratService.findAll();
        
        // Calculer les montants totaux pour chaque contrat
        Map<Long, BigDecimal> montantsTotaux = contrats.stream()
                .collect(Collectors.toMap(
                        PubContrat::getId,
                        pubContratService::calculerMontantTotal
                ));
        
        model.addAttribute("contrats", contrats);
        model.addAttribute("montantsTotaux", montantsTotaux);
        return "pub-contrats/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("contrat", new PubContrat());
        model.addAttribute("societes", societeService.findAll());
        model.addAttribute("configurations", configurationPrixService.findAll());
        return "pub-contrats/form";
    }

    @PostMapping
    public String save(@ModelAttribute PubContrat contrat) {
        pubContratService.save(contrat);
        return "redirect:/pub-contrats";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        pubContratService.findById(id).ifPresent(contrat -> model.addAttribute("contrat", contrat));
        model.addAttribute("societes", societeService.findAll());
        model.addAttribute("configurations", configurationPrixService.findAll());
        return "pub-contrats/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        pubContratService.deleteById(id);
        return "redirect:/pub-contrats";
    }
}

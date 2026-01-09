package com.example.airline.controller;

import com.example.airline.model.TarifVolClasse;
import com.example.airline.model.Vol;
import com.example.airline.service.TarifVolClasseService;
import com.example.airline.service.VolService;
import com.example.airline.service.ClasseVoyageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tarifs")
public class TarifVolClasseController {

    private final TarifVolClasseService tarifService;
    private final VolService volService;
    private final ClasseVoyageService classeVoyageService;

    public TarifVolClasseController(TarifVolClasseService tarifService,
                                     VolService volService,
                                     ClasseVoyageService classeVoyageService) {
        this.tarifService = tarifService;
        this.volService = volService;
        this.classeVoyageService = classeVoyageService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tarifs", tarifService.findAll());
        return "tarifs/list";
    }

    @GetMapping("/new")
    public String createForm(@RequestParam(required = false) Long volId, Model model) {
        TarifVolClasse tarif = new TarifVolClasse();
        
        // Pré-sélectionner le vol si volId est fourni
        if (volId != null) {
            volService.findById(volId).ifPresent(tarif::setVol);
        }
        
        model.addAttribute("tarif", tarif);
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("classes", classeVoyageService.findAll());
        return "tarifs/form";
    }

    @PostMapping
    public String save(@ModelAttribute TarifVolClasse tarif) {
        tarifService.save(tarif);
        return "redirect:/tarifs";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        tarifService.findById(id).ifPresent(tarif -> model.addAttribute("tarif", tarif));
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("classes", classeVoyageService.findAll());
        return "tarifs/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        tarifService.deleteById(id);
        return "redirect:/tarifs";
    }
}
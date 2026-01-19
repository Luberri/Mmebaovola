package com.example.airline.controller;

import com.example.airline.model.TarifVolClasseType;
import com.example.airline.service.ClasseVoyageService;
import com.example.airline.service.TarifVolClasseTypeService;
import com.example.airline.service.VolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tarifs")
public class TarifVolClasseTypeController {

    private final TarifVolClasseTypeService tarifService;
    private final VolService volService;
    private final ClasseVoyageService classeVoyageService;

    public TarifVolClasseTypeController(TarifVolClasseTypeService tarifService,
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
    public String createForm(Model model) {
        model.addAttribute("tarif", new TarifVolClasseType());
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("classes", classeVoyageService.findAll());
        model.addAttribute("tarifsReference", tarifService.findAll()); // Pour la liste de référence
        return "tarifs/form";
    }

    @PostMapping
    public String save(@ModelAttribute TarifVolClasseType tarif,
                       @RequestParam(required = false) Long tarifReferenceId) {
        // Si un tarif de référence est sélectionné, le charger
        if (tarifReferenceId != null) {
            tarifService.findById(tarifReferenceId).ifPresent(tarif::setTarifReference);
        } else {
            tarif.setTarifReference(null);
            tarif.setPourcentage(null);
        }
        tarifService.save(tarif);
        return "redirect:/tarifs";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        tarifService.findById(id).ifPresent(tarif -> model.addAttribute("tarif", tarif));
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("classes", classeVoyageService.findAll());
        model.addAttribute("tarifsReference", tarifService.findAll());
        return "tarifs/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        tarifService.deleteById(id);
        return "redirect:/tarifs";
    }
}
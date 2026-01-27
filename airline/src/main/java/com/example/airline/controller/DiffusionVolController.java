package com.example.airline.controller;

import com.example.airline.model.DiffusionVol;
import com.example.airline.service.DiffusionVolService;
import com.example.airline.service.PubContratService;
import com.example.airline.service.VolService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/diffusions-vol")
public class DiffusionVolController {

    private final DiffusionVolService diffusionVolService;
    private final PubContratService pubContratService;
    private final VolService volService;

    public DiffusionVolController(DiffusionVolService diffusionVolService,
                                   PubContratService pubContratService,
                                   VolService volService) {
        this.diffusionVolService = diffusionVolService;
        this.pubContratService = pubContratService;
        this.volService = volService;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        
        List<DiffusionVol> diffusions;
        BigDecimal chiffreAffaires = null;
        
        if (dateDebut != null && dateFin != null) {
            diffusions = diffusionVolService.findByDateRange(dateDebut, dateFin);
            chiffreAffaires = diffusionVolService.calculateChiffreAffaires(dateDebut, dateFin);
        } else {
            diffusions = diffusionVolService.findAll();
        }
        
        model.addAttribute("diffusions", diffusions);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("chiffreAffaires", chiffreAffaires);
        
        return "diffusions-vol/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("diffusion", new DiffusionVol());
        model.addAttribute("contrats", pubContratService.findAll());
        model.addAttribute("vols", volService.findAll());
        return "diffusions-vol/form";
    }

    @PostMapping
    public String save(@ModelAttribute DiffusionVol diffusion) {
        diffusionVolService.save(diffusion);
        return "redirect:/diffusions-vol";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        diffusionVolService.findById(id).ifPresent(diffusion -> model.addAttribute("diffusion", diffusion));
        model.addAttribute("contrats", pubContratService.findAll());
        model.addAttribute("vols", volService.findAll());
        return "diffusions-vol/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        diffusionVolService.deleteById(id);
        return "redirect:/diffusions-vol";
    }
}

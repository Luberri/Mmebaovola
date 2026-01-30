// filepath: src/main/java/com/example/airline/controller/VolController.java
package com.example.airline.controller;

import com.example.airline.model.Vol;
import com.example.airline.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public String list(Model model,
                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
    
    List<Vol> vols;
    
    // Filtrer par date si les paramètres sont fournis
    if (dateDebut != null && dateFin != null) {
        vols = volService.findByDateRange(dateDebut, dateFin);
    } else if (dateDebut != null) {
        vols = volService.findByDateFrom(dateDebut);
    } else if (dateFin != null) {
        vols = volService.findByDateTo(dateFin);
    } else {
        vols = volService.findAll();
    }
    
    // Total généré par les réservations (billets vendus)
    Map<Long, BigDecimal> revenusReservations = vols.stream()
            .collect(Collectors.toMap(Vol::getId, volService::calculateReservationRevenue));
    
    // Total des produits extra vendus
    Map<Long, BigDecimal> produitExtraRevenues = vols.stream()
            .collect(Collectors.toMap(Vol::getId, volService::calculateProduitExtraRevenueForVol));
    
    // Montants estimés des diffusions pub (nbrDiffusion * prix unitaire)
    Map<Long, BigDecimal> pubEstimatedRevenues = vols.stream()
            .collect(Collectors.toMap(Vol::getId, volService::calculateEstimatedPubRevenueForVol));
    
    // Montants déjà payés pour les diffusions pub
    Map<Long, BigDecimal> pubPaidRevenues = vols.stream()
            .collect(Collectors.toMap(Vol::getId, volService::calculateActualPubRevenueForVol));
    
    // Reste à payer pour les diffusions pub
    Map<Long, BigDecimal> pubRemainingRevenues = vols.stream()
            .collect(Collectors.toMap(Vol::getId, volService::calculateRemainingPubRevenueForVol));
    
    // Total global (réservations + produits extra + pub estimé)
    Map<Long, BigDecimal> totalRevenues = vols.stream()
            .collect(Collectors.toMap(
                Vol::getId, 
                vol -> volService.calculateReservationRevenue(vol)
                        .add(volService.calculateProduitExtraRevenueForVol(vol))
                        .add(volService.calculateEstimatedPubRevenueForVol(vol))
            ));
    
    // Calcul des totaux globaux pour le résumé
    BigDecimal totalRevenusReservations = revenusReservations.values().stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalProduitExtra = produitExtraRevenues.values().stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalPubEstimated = pubEstimatedRevenues.values().stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalPubPaid = pubPaidRevenues.values().stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalPubRemaining = pubRemainingRevenues.values().stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal grandTotal = totalRevenues.values().stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    model.addAttribute("vols", vols);
    model.addAttribute("revenusReservations", revenusReservations);
    model.addAttribute("produitExtraRevenues", produitExtraRevenues);
    model.addAttribute("pubEstimatedRevenues", pubEstimatedRevenues);
    model.addAttribute("pubPaidRevenues", pubPaidRevenues);
    model.addAttribute("pubRemainingRevenues", pubRemainingRevenues);
    model.addAttribute("totalRevenues", totalRevenues);
    
    // Filtres
    model.addAttribute("dateDebut", dateDebut);
    model.addAttribute("dateFin", dateFin);
    
    // Totaux globaux
    model.addAttribute("totalRevenusReservations", totalRevenusReservations);
    model.addAttribute("totalProduitExtra", totalProduitExtra);
    model.addAttribute("totalPubEstimated", totalPubEstimated);
    model.addAttribute("totalPubPaid", totalPubPaid);
    model.addAttribute("totalPubRemaining", totalPubRemaining);
    model.addAttribute("grandTotal", grandTotal);
    
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
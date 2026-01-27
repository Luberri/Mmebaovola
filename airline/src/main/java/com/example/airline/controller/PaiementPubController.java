package com.example.airline.controller;

import com.example.airline.model.DiffusionVol;
import com.example.airline.model.PaiementPub;
import com.example.airline.model.Societe;
import com.example.airline.service.DiffusionVolService;
import com.example.airline.service.PaiementPubService;
import com.example.airline.service.SocieteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/paiements-pub")
public class PaiementPubController {

    private final PaiementPubService paiementPubService;
    private final DiffusionVolService diffusionVolService;
    private final SocieteService societeService;

    public PaiementPubController(PaiementPubService paiementPubService, 
                                  DiffusionVolService diffusionVolService,
                                  SocieteService societeService) {
        this.paiementPubService = paiementPubService;
        this.diffusionVolService = diffusionVolService;
        this.societeService = societeService;
    }

    @GetMapping
    public String list(Model model, @RequestParam(required = false) Long societeId) {
        List<PaiementPub> paiements;
        List<DiffusionVol> diffusions;
        Societe selectedSociete = null;
        
        // Filtre par société
        if (societeId != null) {
            selectedSociete = societeService.findById(societeId).orElse(null);
            if (selectedSociete != null) {
                diffusions = diffusionVolService.findBySociete(selectedSociete);
                paiements = paiementPubService.findBySociete(selectedSociete);
            } else {
                diffusions = diffusionVolService.findAll();
                paiements = paiementPubService.findAll();
            }
        } else {
            diffusions = diffusionVolService.findAll();
            paiements = paiementPubService.findAll();
        }
        
        Map<Long, BigDecimal> montantsTotaux = new HashMap<>();
        Map<Long, BigDecimal> montantsPayes = new HashMap<>();
        Map<Long, BigDecimal> restesAPayer = new HashMap<>();
        
        for (DiffusionVol diffusion : diffusions) {
            montantsTotaux.put(diffusion.getId(), paiementPubService.getMontantTotal(diffusion));
            montantsPayes.put(diffusion.getId(), paiementPubService.getTotalPaye(diffusion));
            restesAPayer.put(diffusion.getId(), paiementPubService.getResteAPayer(diffusion));
        }
        
        // Calcul des totaux globaux
        BigDecimal totalMontantPaiements = paiements.stream()
                .map(PaiementPub::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalDu = diffusions.stream()
                .map(paiementPubService::getMontantTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalPaye = diffusions.stream()
                .map(paiementPubService::getTotalPaye)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalReste = totalDu.subtract(totalPaye);
        
        model.addAttribute("paiements", paiements);
        model.addAttribute("diffusions", diffusions);
        model.addAttribute("montantsTotaux", montantsTotaux);
        model.addAttribute("montantsPayes", montantsPayes);
        model.addAttribute("restesAPayer", restesAPayer);
        model.addAttribute("societes", societeService.findAll());
        model.addAttribute("selectedSociete", selectedSociete);
        model.addAttribute("selectedSocieteId", societeId);
        model.addAttribute("totalMontantPaiements", totalMontantPaiements);
        model.addAttribute("totalDu", totalDu);
        model.addAttribute("totalPaye", totalPaye);
        model.addAttribute("totalReste", totalReste);
        
        return "paiements-pub/list";
    }

    @GetMapping("/new")
    public String createForm(Model model, @RequestParam(required = false) Long diffusionId) {
        PaiementPub paiement = new PaiementPub();
        paiement.setDatePaiement(LocalDateTime.now());
        
        if (diffusionId != null) {
            diffusionVolService.findById(diffusionId).ifPresent(diffusion -> {
                paiement.setDiffusionVol(diffusion);
                model.addAttribute("resteAPayer", paiementPubService.getResteAPayer(diffusion));
            });
        }
        
        model.addAttribute("paiement", paiement);
        model.addAttribute("diffusions", diffusionVolService.findAll());
        return "paiements-pub/form";
    }

    @GetMapping("/new-by-societe")
    public String createFormBySociete(Model model, @RequestParam(required = false) Long societeId) {
        List<Societe> societes = societeService.findAll();
        model.addAttribute("societes", societes);
        
        if (societeId != null) {
            societeService.findById(societeId).ifPresent(societe -> {
                model.addAttribute("selectedSociete", societe);
                
                List<DiffusionVol> diffusions = diffusionVolService.findBySociete(societe);
                model.addAttribute("diffusions", diffusions);
                
                BigDecimal totalDu = BigDecimal.ZERO;
                BigDecimal totalPaye = BigDecimal.ZERO;
                Map<Long, BigDecimal> montantsParDiffusion = new HashMap<>();
                Map<Long, BigDecimal> restesParDiffusion = new HashMap<>();
                int nbDiffusionsAvecReste = 0;
                
                for (DiffusionVol diffusion : diffusions) {
                    BigDecimal montant = paiementPubService.getMontantTotal(diffusion);
                    BigDecimal paye = paiementPubService.getTotalPaye(diffusion);
                    BigDecimal reste = paiementPubService.getResteAPayer(diffusion);
                    
                    totalDu = totalDu.add(montant);
                    totalPaye = totalPaye.add(paye);
                    montantsParDiffusion.put(diffusion.getId(), montant);
                    restesParDiffusion.put(diffusion.getId(), reste);
                    
                    if (reste.compareTo(BigDecimal.ZERO) > 0) {
                        nbDiffusionsAvecReste++;
                    }
                }
                
                model.addAttribute("totalDu", totalDu);
                model.addAttribute("totalPaye", totalPaye);
                model.addAttribute("resteAPayer", totalDu.subtract(totalPaye));
                model.addAttribute("montantsParDiffusion", montantsParDiffusion);
                model.addAttribute("restesParDiffusion", restesParDiffusion);
                model.addAttribute("nbDiffusionsAvecReste", nbDiffusionsAvecReste);
            });
        }
        
        return "paiements-pub/form-by-societe";
    }

    @PostMapping("/pay-by-societe")
    public String payBySociete(@RequestParam Long societeId, 
                                @RequestParam BigDecimal montant,
                                @RequestParam(required = false) String description,
                                Model model) {
        
        Societe societe = societeService.findById(societeId)
                .orElseThrow(() -> new IllegalArgumentException("Société introuvable"));
        
        List<DiffusionVol> diffusions = diffusionVolService.findBySociete(societe);
        List<DiffusionVol> diffusionsAvecReste = new ArrayList<>();
        Map<DiffusionVol, BigDecimal> restesParDiffusion = new HashMap<>();
        BigDecimal totalResteAPayer = BigDecimal.ZERO;
        
        for (DiffusionVol diffusion : diffusions) {
            BigDecimal reste = paiementPubService.getResteAPayer(diffusion);
            if (reste.compareTo(BigDecimal.ZERO) > 0) {
                diffusionsAvecReste.add(diffusion);
                restesParDiffusion.put(diffusion, reste);
                totalResteAPayer = totalResteAPayer.add(reste);
            }
        }
        
        if (diffusionsAvecReste.isEmpty()) {
            model.addAttribute("error", "Aucune diffusion avec un reste à payer pour cette société");
            return createFormBySociete(model, societeId);
        }
        
        if (montant.compareTo(totalResteAPayer) > 0) {
            model.addAttribute("error", "Le montant ne peut pas dépasser le reste à payer total (" + totalResteAPayer + " Ar)");
            return createFormBySociete(model, societeId);
        }
        
        BigDecimal pourcentageGlobal = montant.multiply(BigDecimal.valueOf(100))
                .divide(totalResteAPayer, 2, RoundingMode.HALF_UP);
        
        BigDecimal montantDistribue = BigDecimal.ZERO;
        int nbDiffusions = diffusionsAvecReste.size();
        
        for (int i = 0; i < nbDiffusions; i++) {
            DiffusionVol diffusion = diffusionsAvecReste.get(i);
            BigDecimal resteDiffusion = restesParDiffusion.get(diffusion);
            
            BigDecimal montantPourCetteDiffusion;
            
            if (i == nbDiffusions - 1) {
                montantPourCetteDiffusion = montant.subtract(montantDistribue);
            } else {
                montantPourCetteDiffusion = montant
                        .multiply(resteDiffusion)
                        .divide(totalResteAPayer, 2, RoundingMode.HALF_UP);
                
                if (montantPourCetteDiffusion.compareTo(resteDiffusion) > 0) {
                    montantPourCetteDiffusion = resteDiffusion;
                }
            }
            
            if (montantPourCetteDiffusion.compareTo(BigDecimal.ZERO) > 0) {
                PaiementPub paiement = new PaiementPub();
                paiement.setDiffusionVol(diffusion);
                paiement.setMontant(montantPourCetteDiffusion);
                paiement.setDatePaiement(LocalDateTime.now());
                paiement.setDescription(description != null && !description.isBlank() ? description : 
                        "Paiement société " + societe.getNom() + " (" + pourcentageGlobal + "%)");
                
                paiementPubService.save(paiement);
                montantDistribue = montantDistribue.add(montantPourCetteDiffusion);
            }
        }
        
        return "redirect:/paiements-pub";
    }

    @PostMapping
    public String save(@ModelAttribute PaiementPub paiement, Model model) {
        if (paiement.getDiffusionVol() != null) {
            DiffusionVol diffusion = diffusionVolService.findById(paiement.getDiffusionVol().getId())
                    .orElse(null);
            
            if (diffusion != null) {
                BigDecimal resteAPayer = paiementPubService.getResteAPayer(diffusion);
                
                if (paiement.getMontant().compareTo(resteAPayer) > 0) {
                    model.addAttribute("error", "Le montant ne peut pas dépasser le reste à payer (" + resteAPayer + " Ar)");
                    model.addAttribute("paiement", paiement);
                    model.addAttribute("diffusions", diffusionVolService.findAll());
                    model.addAttribute("resteAPayer", resteAPayer);
                    return "paiements-pub/form";
                }
            }
        }
        
        paiementPubService.save(paiement);
        return "redirect:/paiements-pub";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        paiementPubService.findById(id).ifPresent(paiement -> {
            model.addAttribute("paiement", paiement);
            if (paiement.getDiffusionVol() != null) {
                model.addAttribute("resteAPayer", 
                    paiementPubService.getResteAPayer(paiement.getDiffusionVol())
                        .add(paiement.getMontant()));
            }
        });
        model.addAttribute("diffusions", diffusionVolService.findAll());
        return "paiements-pub/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        paiementPubService.deleteById(id);
        return "redirect:/paiements-pub";
    }

    @GetMapping("/diffusion/{diffusionId}")
    public String listByDiffusion(@PathVariable Long diffusionId, Model model) {
        DiffusionVol diffusion = diffusionVolService.findById(diffusionId)
                .orElseThrow(() -> new IllegalArgumentException("Diffusion introuvable"));
        
        List<PaiementPub> paiements = paiementPubService.findByDiffusionVol(diffusion);
        
        model.addAttribute("diffusion", diffusion);
        model.addAttribute("paiements", paiements);
        model.addAttribute("montantTotal", paiementPubService.getMontantTotal(diffusion));
        model.addAttribute("totalPaye", paiementPubService.getTotalPaye(diffusion));
        model.addAttribute("resteAPayer", paiementPubService.getResteAPayer(diffusion));
        model.addAttribute("isPaiementComplet", paiementPubService.isPaiementComplet(diffusion));
        
        return "paiements-pub/diffusion-detail";
    }
}

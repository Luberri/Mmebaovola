package com.example.airline.controller;

import com.example.airline.model.ProduitExtraVente;
import com.example.airline.service.ProduitExtraService;
import com.example.airline.service.ProduitExtraVenteService;
import com.example.airline.service.VolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/produits-extra-vente")
public class ProduitExtraVenteController {

    private final ProduitExtraVenteService produitExtraVenteService;
    private final ProduitExtraService produitExtraService;
    private final VolService volService;

    public ProduitExtraVenteController(ProduitExtraVenteService produitExtraVenteService,
                                        ProduitExtraService produitExtraService,
                                        VolService volService) {
        this.produitExtraVenteService = produitExtraVenteService;
        this.produitExtraService = produitExtraService;
        this.volService = volService;
    }

    @GetMapping
    public String list(Model model) {
        var ventes = produitExtraVenteService.findAll();
        Map<Long, BigDecimal> montants = new HashMap<>();
        
        for (ProduitExtraVente vente : ventes) {
            montants.put(vente.getId(), produitExtraVenteService.calculerMontantVente(vente));
        }
        
        model.addAttribute("ventes", ventes);
        model.addAttribute("montants", montants);
        return "produits-extra-vente/list"; // Correction: ajout du 's'
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("vente", new ProduitExtraVente());
        model.addAttribute("produits", produitExtraService.findAll());
        model.addAttribute("vols", volService.findAll());
        return "produits-extra-vente/form"; // Correction: ajout du 's'
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ProduitExtraVente vente, 
                         @RequestParam Long produitId,
                         @RequestParam Long volId,
                         RedirectAttributes redirectAttributes) {
        produitExtraService.findById(produitId).ifPresent(vente::setProduit);
        volService.findById(volId).ifPresent(vente::setVol);
        
        produitExtraVenteService.save(vente);
        redirectAttributes.addFlashAttribute("success", "Vente créée avec succès");
        return "redirect:/produits-extra-vente";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        return produitExtraVenteService.findById(id)
                .map(vente -> {
                    model.addAttribute("vente", vente);
                    model.addAttribute("produits", produitExtraService.findAll());
                    model.addAttribute("vols", volService.findAll());
                    return "produits-extra-vente/form"; // Correction: ajout du 's'
                })
                .orElse("redirect:/produits-extra-vente");
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, 
                         @ModelAttribute ProduitExtraVente vente,
                         @RequestParam Long produitId,
                         @RequestParam Long volId,
                         RedirectAttributes redirectAttributes) {
        vente.setId(id);
        produitExtraService.findById(produitId).ifPresent(vente::setProduit);
        volService.findById(volId).ifPresent(vente::setVol);
        
        produitExtraVenteService.save(vente);
        redirectAttributes.addFlashAttribute("success", "Vente modifiée avec succès");
        return "redirect:/produits-extra-vente";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        produitExtraVenteService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Vente supprimée avec succès");
        return "redirect:/produits-extra-vente";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        return produitExtraVenteService.findById(id)
                .map(vente -> {
                    model.addAttribute("vente", vente);
                    model.addAttribute("montant", produitExtraVenteService.calculerMontantVente(vente));
                    return "produits-extra-vente/view"; // Correction: ajout du 's'
                })
                .orElse("redirect:/produits-extra-vente");
    }
}

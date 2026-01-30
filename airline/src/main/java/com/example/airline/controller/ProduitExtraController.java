package com.example.airline.controller;

import com.example.airline.model.ProduitExtra;
import com.example.airline.service.ProduitExtraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produits-extra")
public class ProduitExtraController {

    private final ProduitExtraService produitExtraService;

    public ProduitExtraController(ProduitExtraService produitExtraService) {
        this.produitExtraService = produitExtraService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("produitsExtra", produitExtraService.findAll());
        return "produit-extra/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("produitExtra", new ProduitExtra());
        return "produit-extra/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ProduitExtra produitExtra, RedirectAttributes redirectAttributes) {
        produitExtraService.save(produitExtra);
        redirectAttributes.addFlashAttribute("success", "Produit extra créé avec succès");
        return "redirect:/produits-extra";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        return produitExtraService.findById(id)
                .map(produitExtra -> {
                    model.addAttribute("produitExtra", produitExtra);
                    return "produit-extra/form";
                })
                .orElse("redirect:/produits-extra");
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute ProduitExtra produitExtra, 
                         RedirectAttributes redirectAttributes) {
        produitExtra.setId(id);
        produitExtraService.save(produitExtra);
        redirectAttributes.addFlashAttribute("success", "Produit extra modifié avec succès");
        return "redirect:/produits-extra";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        produitExtraService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Produit extra supprimé avec succès");
        return "redirect:/produits-extra";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        return produitExtraService.findById(id)
                .map(produitExtra -> {
                    model.addAttribute("produitExtra", produitExtra);
                    return "produit-extra/view";
                })
                .orElse("redirect:/produits-extra");
    }
}

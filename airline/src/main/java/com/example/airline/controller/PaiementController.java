// filepath: src/main/java/com/example/airline/controller/PaiementController.java
package com.example.airline.controller;

import com.example.airline.model.Paiement;
import com.example.airline.service.PaiementService;
import com.example.airline.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/paiements")
public class PaiementController {

    private final PaiementService paiementService;
    private final ReservationService reservationService;

    public PaiementController(PaiementService paiementService, ReservationService reservationService) {
        this.paiementService = paiementService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("paiements", paiementService.findAll());
        return "paiements/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("paiement", new Paiement());
        model.addAttribute("reservations", reservationService.findAll());
        return "paiements/form";
    }

    @PostMapping
    public String save(@ModelAttribute Paiement paiement) {
        paiementService.save(paiement);
        return "redirect:/paiements";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        paiementService.findById(id).ifPresent(paiement -> model.addAttribute("paiement", paiement));
        model.addAttribute("reservations", reservationService.findAll());
        return "paiements/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        paiementService.deleteById(id);
        return "redirect:/paiements";
    }
}
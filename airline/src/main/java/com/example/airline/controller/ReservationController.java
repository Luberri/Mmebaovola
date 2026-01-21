// filepath: src/main/java/com/example/airline/controller/ReservationController.java
package com.example.airline.controller;

import com.example.airline.model.Reservation;
import com.example.airline.model.ReservationSiege;
import com.example.airline.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final PassagerService passagerService;
    private final VolService volService;
    private final ClasseVoyageService classeVoyageService;
    private final TarifVolClasseTypeService tarifVolClasseTypeService;
    private final ReservationSiegeService reservationSiegeService;

    public ReservationController(
        ReservationService reservationService,
        PassagerService passagerService,
        VolService volService,
        ClasseVoyageService classeVoyageService,
        TarifVolClasseTypeService tarifVolClasseTypeService,
        ReservationSiegeService reservationSiegeService
    ) {
        this.reservationService = reservationService;
        this.passagerService = passagerService;
        this.volService = volService;
        this.classeVoyageService = classeVoyageService;
        this.tarifVolClasseTypeService = tarifVolClasseTypeService;
        this.reservationSiegeService = reservationSiegeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservations/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("passagers", passagerService.findAll());
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("classes", classeVoyageService.findAll());
        model.addAttribute("tarifs", tarifVolClasseTypeService.findAll());
        return "reservations/form";
    }

    @PostMapping
    public String save(@ModelAttribute Reservation reservation,
                       @RequestParam("numSieges") String numSieges,
                       Model model) {
        // Vérification des sièges déjà réservés
        String[] sieges = numSieges.split(",");
        for (String s : sieges) {
            String siege = s.trim();
            if (reservationSiegeService.existsByNumSiegeAndVol(siege, reservation.getVol())) {
                model.addAttribute("error", "Le siège " + siege + " est déjà réservé pour ce vol.");
                // Recharger les listes pour le formulaire
                model.addAttribute("reservation", reservation);
                model.addAttribute("passagers", passagerService.findAll());
                model.addAttribute("vols", volService.findAll());
                model.addAttribute("classes", classeVoyageService.findAll());
                model.addAttribute("tarifs", tarifVolClasseTypeService.findAll());
                model.addAttribute("numSieges", numSieges);
                return "reservations/form";
            }
        }
        Reservation saved = reservationService.save(reservation);
        for (String s : sieges) {
            ReservationSiege rs = new ReservationSiege();
            rs.setNumSiege(s.trim());
            rs.setReservation(saved);
            reservationSiegeService.save(rs);
        }
        return "redirect:/reservations";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        reservationService.findById(id).ifPresent(reservation -> model.addAttribute("reservation", reservation));
        model.addAttribute("passagers", passagerService.findAll());
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("classes", classeVoyageService.findAll());
        model.addAttribute("tarifs", tarifVolClasseTypeService.findAll());
        return "reservations/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return "redirect:/reservations";
    }
}
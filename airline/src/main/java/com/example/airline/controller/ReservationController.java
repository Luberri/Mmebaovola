// filepath: src/main/java/com/example/airline/controller/ReservationController.java
package com.example.airline.controller;

import com.example.airline.model.Reservation;
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
    private final TypeTarifService typeTarifService;

    public ReservationController(ReservationService reservationService, PassagerService passagerService,
                                  VolService volService, ClasseVoyageService classeVoyageService,
                                  TypeTarifService typeTarifService) {
        this.reservationService = reservationService;
        this.passagerService = passagerService;
        this.volService = volService;
        this.classeVoyageService = classeVoyageService;
        this.typeTarifService = typeTarifService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        // The template 'reservations/list' now uses Nav.html as layout
        return "reservations/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("passagers", passagerService.findAll());
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("classes", classeVoyageService.findAll());
        model.addAttribute("typesTarif", typeTarifService.findAll());
        return "reservations/form";
    }

    @PostMapping
    public String save(@ModelAttribute Reservation reservation) {
        reservationService.save(reservation);
        return "redirect:/reservations";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        reservationService.findById(id).ifPresent(reservation -> model.addAttribute("reservation", reservation));
        model.addAttribute("passagers", passagerService.findAll());
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("classes", classeVoyageService.findAll());
        model.addAttribute("typesTarif", typeTarifService.findAll());
        return "reservations/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return "redirect:/reservations";
    }
}
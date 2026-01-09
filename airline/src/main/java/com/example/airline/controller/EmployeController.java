// filepath: src/main/java/com/example/airline/controller/EmployeController.java
package com.example.airline.controller;

import com.example.airline.model.Employe;
import com.example.airline.service.EmployeService;
import com.example.airline.service.CompagnieAerienneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    private final EmployeService employeService;
    private final CompagnieAerienneService compagnieService;

    public EmployeController(EmployeService employeService, CompagnieAerienneService compagnieService) {
        this.employeService = employeService;
        this.compagnieService = compagnieService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employes", employeService.findAll());
        return "employes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("employe", new Employe());
        model.addAttribute("compagnies", compagnieService.findAll());
        return "employes/form";
    }

    @PostMapping
    public String save(@ModelAttribute Employe employe) {
        employeService.save(employe);
        return "redirect:/employes";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        employeService.findById(id).ifPresent(employe -> model.addAttribute("employe", employe));
        model.addAttribute("compagnies", compagnieService.findAll());
        return "employes/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeService.deleteById(id);
        return "redirect:/employes";
    }
}
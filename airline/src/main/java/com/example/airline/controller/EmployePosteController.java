package com.example.airline.controller;

import com.example.airline.model.EmployePoste;
import com.example.airline.service.EmployePosteService;
import com.example.airline.service.EmployeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employe-postes")
public class EmployePosteController {

    private final EmployePosteService employePosteService;
    private final EmployeService employeService;

    public EmployePosteController(EmployePosteService employePosteService, EmployeService employeService) {
        this.employePosteService = employePosteService;
        this.employeService = employeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employePostes", employePosteService.findAll());
        return "employe-postes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("employePoste", new EmployePoste());
        model.addAttribute("employes", employeService.findAll());
        return "employe-postes/form";
    }

    @PostMapping
    public String save(@ModelAttribute EmployePoste employePoste) {
        employePosteService.save(employePoste);
        return "redirect:/employe-postes";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        employePosteService.findById(id).ifPresent(ep -> model.addAttribute("employePoste", ep));
        model.addAttribute("employes", employeService.findAll());
        return "employe-postes/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employePosteService.deleteById(id);
        return "redirect:/employe-postes";
    }
}
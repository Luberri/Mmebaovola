package com.example.airline.controller;

import com.example.airline.model.AffectationVol;
import com.example.airline.model.enums.RoleVol;
import com.example.airline.service.AffectationVolService;
import com.example.airline.service.EmployeService;
import com.example.airline.service.VolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/affectations-vol")
public class AffectationVolController {

    private final AffectationVolService affectationVolService;
    private final VolService volService;
    private final EmployeService employeService;

    public AffectationVolController(AffectationVolService affectationVolService,
                                     VolService volService,
                                     EmployeService employeService) {
        this.affectationVolService = affectationVolService;
        this.volService = volService;
        this.employeService = employeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("affectations", affectationVolService.findAll());
        return "affectations/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("affectation", new AffectationVol());
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("employes", employeService.findAll());
        model.addAttribute("roles", RoleVol.values());
        return "affectations/form";
    }

    @PostMapping
    public String save(@ModelAttribute AffectationVol affectation) {
        affectationVolService.save(affectation);
        return "redirect:/affectations-vol";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        affectationVolService.findById(id).ifPresent(affectation -> 
            model.addAttribute("affectation", affectation));
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("employes", employeService.findAll());
        model.addAttribute("roles", RoleVol.values());
        return "affectations/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        affectationVolService.deleteById(id);
        return "redirect:/affectations-vol";
    }
}
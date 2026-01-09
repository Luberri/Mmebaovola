package com.example.airline.controller;

import com.example.airline.model.Utilisateur;
import com.example.airline.model.enums.RoleSysteme;
import com.example.airline.service.UtilisateurService;
import com.example.airline.service.EmployeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final EmployeService employeService;

    public UtilisateurController(UtilisateurService utilisateurService, EmployeService employeService) {
        this.utilisateurService = utilisateurService;
        this.employeService = employeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.findAll());
        return "utilisateurs/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("employes", employeService.findAll());
        model.addAttribute("roles", RoleSysteme.values());
        return "utilisateurs/form";
    }

    @PostMapping
    public String save(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.save(utilisateur);
        return "redirect:/utilisateurs";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        utilisateurService.findById(id).ifPresent(utilisateur -> 
            model.addAttribute("utilisateur", utilisateur));
        model.addAttribute("employes", employeService.findAll());
        model.addAttribute("roles", RoleSysteme.values());
        return "utilisateurs/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        utilisateurService.deleteById(id);
        return "redirect:/utilisateurs";
    }
}
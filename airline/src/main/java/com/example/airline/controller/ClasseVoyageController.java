// filepath: src/main/java/com/example/airline/controller/ClasseVoyageController.java
package com.example.airline.controller;

import com.example.airline.model.ClasseVoyage;
import com.example.airline.service.ClasseVoyageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/classes-voyage")
public class ClasseVoyageController {

    private final ClasseVoyageService classeVoyageService;

    public ClasseVoyageController(ClasseVoyageService classeVoyageService) {
        this.classeVoyageService = classeVoyageService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("classes", classeVoyageService.findAll());
        return "classes/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("classe", new ClasseVoyage());
        return "classes/form";
    }

    @PostMapping
    public String save(@ModelAttribute ClasseVoyage classe) {
        classeVoyageService.save(classe);
        return "redirect:/classes-voyage";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        classeVoyageService.findById(id).ifPresent(classe -> model.addAttribute("classe", classe));
        return "classes/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        classeVoyageService.deleteById(id);
        return "redirect:/classes-voyage";
    }
}
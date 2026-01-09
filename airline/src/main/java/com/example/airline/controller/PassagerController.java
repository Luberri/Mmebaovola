// filepath: src/main/java/com/example/airline/controller/PassagerController.java
package com.example.airline.controller;

import com.example.airline.model.Passager;
import com.example.airline.service.PassagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passagers")
public class PassagerController {

    private final PassagerService passagerService;

    public PassagerController(PassagerService passagerService) {
        this.passagerService = passagerService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("passagers", passagerService.findAll());
        return "passagers/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("passager", new Passager());
        return "passagers/form";
    }

    @PostMapping
    public String save(@ModelAttribute Passager passager) {
        passagerService.save(passager);
        return "redirect:/passagers";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        passagerService.findById(id).ifPresent(passager -> model.addAttribute("passager", passager));
        return "passagers/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        passagerService.deleteById(id);
        return "redirect:/passagers";
    }
}
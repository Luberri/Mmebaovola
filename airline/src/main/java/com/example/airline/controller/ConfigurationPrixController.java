package com.example.airline.controller;

import com.example.airline.model.ConfigurationPrix;
import com.example.airline.service.ConfigurationPrixService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configurations-prix")
public class ConfigurationPrixController {

    private final ConfigurationPrixService configurationPrixService;

    public ConfigurationPrixController(ConfigurationPrixService configurationPrixService) {
        this.configurationPrixService = configurationPrixService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("configurations", configurationPrixService.findAll());
        return "configurations-prix/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("configuration", new ConfigurationPrix());
        return "configurations-prix/form";
    }

    @PostMapping
    public String save(@ModelAttribute ConfigurationPrix configuration) {
        configurationPrixService.save(configuration);
        return "redirect:/configurations-prix";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        configurationPrixService.findById(id).ifPresent(config -> model.addAttribute("configuration", config));
        return "configurations-prix/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        configurationPrixService.deleteById(id);
        return "redirect:/configurations-prix";
    }
}

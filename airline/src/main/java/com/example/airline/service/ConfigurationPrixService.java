package com.example.airline.service;

import com.example.airline.model.ConfigurationPrix;
import com.example.airline.repository.ConfigurationPrixRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationPrixService {

    private final ConfigurationPrixRepository configurationPrixRepository;

    public ConfigurationPrixService(ConfigurationPrixRepository configurationPrixRepository) {
        this.configurationPrixRepository = configurationPrixRepository;
    }

    public List<ConfigurationPrix> findAll() {
        return configurationPrixRepository.findAll();
    }

    public Optional<ConfigurationPrix> findById(Long id) {
        return configurationPrixRepository.findById(id);
    }

    public ConfigurationPrix save(ConfigurationPrix configurationPrix) {
        return configurationPrixRepository.save(configurationPrix);
    }

    public void deleteById(Long id) {
        configurationPrixRepository.deleteById(id);
    }
}

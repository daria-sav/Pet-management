package com.example.backend.controller;

import com.example.backend.repository.CountryRepository;
import com.example.backend.repository.PetTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lookups")
public class LookupsController {
    private final PetTypeRepository petTypeRepository;
    private final CountryRepository countryRepository;

    public LookupsController(PetTypeRepository petTypeRepository, CountryRepository countryRepository) {
        this.petTypeRepository = petTypeRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/pet-types")
    public Object petTypes() { 
        return petTypeRepository.findAll(); 
    }

    @GetMapping("/countries")
    public Object countries() { 
        return countryRepository.findAll(); 
    }
}

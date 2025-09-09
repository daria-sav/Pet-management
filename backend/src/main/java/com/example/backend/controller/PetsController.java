package com.example.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.PetDto;
import com.example.backend.dto.PetUpsertDto;
import com.example.backend.service.PetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pets")
public class PetsController {
    private final PetService petService;

    public PetsController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<PetDto> list() { return petService.listForCurrentUser(); }

    @GetMapping("/{id}")
    public PetDto getOne(@PathVariable Long id, Authentication auth) {
        return petService.getOne(id, auth.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDto create(@RequestBody @Valid PetUpsertDto dto) {
        return petService.createForCurrentUser(dto);
    }

    @PutMapping("/{id}")
    public PetDto update(@PathVariable Long id, @RequestBody @Valid PetUpsertDto dto) {
        return petService.updateForCurrentUser(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        petService.deactivate(id);
    }

    @GetMapping("/check-identification")
    public Map<String, Boolean> checkIdentification(@RequestParam("value") String value, @RequestParam(value = "excludeId", required = false) Long excludeId) {
        boolean available = petService.isIdentificationAvailable(value, excludeId);
        return Map.of("available", available);
    }
}

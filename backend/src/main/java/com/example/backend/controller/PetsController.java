package com.example.backend.controller;

import com.example.backend.dto.PetDto;
import com.example.backend.dto.PetUpsertDto;
import com.example.backend.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetsController {
    private final PetService petService;

    public PetsController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<PetDto> list() { return petService.listForCurrentUser(); }

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
    public void delete(@PathVariable Long id) {
        petService.deleteForCurrentUser(id);
    }
}

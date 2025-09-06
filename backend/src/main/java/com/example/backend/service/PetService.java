package com.example.backend.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dto.PetDto;
import com.example.backend.dto.PetUpsertDto;
import com.example.backend.entity.Pet;
import com.example.backend.entity.User;
import com.example.backend.repository.CountryRepository;
import com.example.backend.repository.PetRepository;
import com.example.backend.repository.PetTypeRepository;
import com.example.backend.repository.UserRepository;

@Service
public class PetService {
    private final PetRepository petRepo;
    private final UserRepository userRepo;
    private final PetTypeRepository typeRepo;
    private final CountryRepository countryRepo;

    public PetService(PetRepository petRepo, UserRepository userRepo, PetTypeRepository typeRepo, CountryRepository countryRepo) {
        this.petRepo = petRepo;
        this.userRepo = userRepo;
        this.typeRepo = typeRepo;
        this.countryRepo = countryRepo;
    }

    @Transactional(readOnly = true)
    public List<PetDto> listForCurrentUser() {
        Long ownerId = currentUserId();
        return petRepo.findAllByOwner_Id(ownerId).stream().map(this::toDto).toList();
    }

    @Transactional
    public PetDto createForCurrentUser(PetUpsertDto dto) {
        Long ownerId = currentUserId();
        Pet petike = new Pet();
        petike.setName(dto.name);
        petike.setIdentificationNumber(dto.identificationNumber);
        petike.setFurColor(dto.furColor);
        petike.setType(typeRepo.findById(dto.typeId).orElseThrow());
        if (dto.countryId != null) {
            petike.setCountry(countryRepo.findById(dto.countryId).orElseThrow());
        }
        petike.setOwner(userRepo.findById(ownerId).orElseThrow());
        return toDto(petRepo.save(petike));
    }

    @Transactional
    public PetDto updateForCurrentUser(Long id, PetUpsertDto dto) {
        Long ownerId = currentUserId();
        Pet petike = petRepo.findByIdAndOwner_Id(id, ownerId).orElseThrow(); 
        petike.setName(dto.name);
        petike.setIdentificationNumber(dto.identificationNumber);
        petike.setFurColor(dto.furColor);
        petike.setType(typeRepo.findById(dto.typeId).orElseThrow());
        if (dto.countryId != null) {
            petike.setCountry(countryRepo.findById(dto.countryId).orElseThrow());
        } else {
            petike.setCountry(null);
        }
        return toDto(petike);
    }

    @Transactional
    public void deleteForCurrentUser(Long id) {
        Long ownerId = currentUserId();
        Pet petike = petRepo.findByIdAndOwner_Id(id, ownerId).orElseThrow();
        petRepo.delete(petike);
    }

    private Long currentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userRepo.findByUsername(username);
        if (u == null) throw new RuntimeException("User not found: " + username);
        return u.getId();
    }

    private PetDto toDto(Pet petike) {
        PetDto dto = new PetDto();
        dto.id = petike.getId();
        dto.name = petike.getName();
        dto.identificationNumber = petike.getIdentificationNumber();
        dto.furColor = petike.getFurColor();
        dto.typeId = petike.getType().getId();
        dto.typeName = petike.getType().getName();
        dto.countryId = petike.getCountry() == null ? null : petike.getCountry().getId();
        dto.countryName = petike.getCountry() == null ? null : petike.getCountry().getName();
        return dto;
    }
}

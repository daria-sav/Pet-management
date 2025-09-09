package com.example.backend.repository;

import com.example.backend.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    boolean existsByIdentificationNumberAndIdNot(String identificationNumber, Long id);
    boolean existsByIdentificationNumber(String identificationNumber);

    // List<Pet> findAllByOwner_Id(Long ownerId);
    // Optional<Pet> findByIdAndOwner_Id(Long id, Long ownerId);
    // Optional<Pet> findByIdAndOwnerUsername(Long id, String username);
    
    List<Pet> findAllByOwnerIdAndActiveTrueOrderByIdDesc(Long ownerId);
    Optional<Pet> findByIdAndOwner_IdAndActiveTrue(Long id, Long ownerId);
    Optional<Pet> findByIdAndOwnerUsernameAndActiveTrue(Long id, String username);
}
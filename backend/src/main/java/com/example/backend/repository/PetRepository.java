package com.example.backend.repository;

import com.example.backend.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwner_Id(Long ownerId);
    Optional<Pet> findByIdAndOwner_Id(Long id, Long ownerId);
    boolean existsByIdentificationNumber(String identificationNumber);
}
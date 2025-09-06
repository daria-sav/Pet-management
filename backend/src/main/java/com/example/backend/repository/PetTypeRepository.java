package com.example.backend.repository;

import com.example.backend.entity.PetType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PetTypeRepository extends JpaRepository<PetType, Long> { }
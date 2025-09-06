package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PetUpsertDto {
    @NotBlank 
    public String name;
    @NotBlank 
    public String identificationNumber;
    @NotNull  
    public Long typeId;
    @NotBlank 
    public String furColor;
    
    public Long countryId; 
}
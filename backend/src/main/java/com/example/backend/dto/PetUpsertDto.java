package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PetUpsertDto {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    @Pattern(
        regexp = "^[\\p{L}\\p{M}\\d _()]+$",
        message = "Name may contain letters, digits, spaces, underscores and parentheses only"
    )
    public String name;

    @NotBlank(message = "Identification number is required")
    @Pattern(
        regexp = "^[0-9]{1,12}$",
        message = "Identification number must be digits only (max 12)"
    )
    public String identificationNumber;

    @NotNull(message = "Type is required") 
    public Long typeId;

    @NotBlank(message = "Fur color is required")
    @Size(max = 50, message = "Fur color must be at most 50 characters")
    @Pattern(
        regexp = "^[\\p{L}\\p{M} ]+$",
        message = "Fur color must contain letters only"
    )
    public String furColor;
    
    public Long countryId; 
}
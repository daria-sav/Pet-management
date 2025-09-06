package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "identification_number", nullable = false, unique = true)
    private String identificationNumber;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private PetType type;

    @Column(name = "fur_color", nullable = false)
    private String furColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIdentificationNumber() { return identificationNumber; }
    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }
    public PetType getType() { return type; }
    public void setType(PetType type) { this.type = type; }
    public String getFurColor() { return furColor; }
    public void setFurColor(String furColor) { this.furColor = furColor; }
    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}

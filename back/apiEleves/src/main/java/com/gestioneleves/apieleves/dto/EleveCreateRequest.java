package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EleveCreateRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotNull
    @Past
    private LocalDate dateNaissance;
    // Lier éventuellement à un utilisateur (responsable) existant
    private Long utilisateurId;
}

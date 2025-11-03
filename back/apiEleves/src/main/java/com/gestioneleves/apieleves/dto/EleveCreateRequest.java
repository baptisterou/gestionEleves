package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.util.Date;

@Data
public class EleveCreateRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotNull
    @Past
    private Date dateNaissance;
    // Lier éventuellement à un utilisateur (responsable) existant
    private Long utilisateurId;
}

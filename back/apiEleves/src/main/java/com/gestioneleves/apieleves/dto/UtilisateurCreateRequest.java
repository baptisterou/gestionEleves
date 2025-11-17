package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Payload pour la création d'un utilisateur.
 * Ne sera jamais renvoyé en sortie.
 */
public class UtilisateurCreateRequest {
    @NotBlank
    public String nom;
    @NotBlank
    public String prenom;
    @Email @NotBlank
    public String email;
    @NotBlank @Size(min = 8)
    public String motDePasse;
    @NotNull @Past
    public LocalDate dateNaissance;
    @NotBlank
    @Pattern(regexp = "^[0-9+ .-]{8,20}$")
    public String numTel;
}

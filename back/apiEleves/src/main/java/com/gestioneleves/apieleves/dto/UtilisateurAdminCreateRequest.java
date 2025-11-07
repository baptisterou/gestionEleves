package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Payload réservé aux administrateurs pour créer un utilisateur avec un rôle explicite.
 */
public class UtilisateurAdminCreateRequest {
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
    @NotNull
    public Role role; // rôle cible à attribuer
}

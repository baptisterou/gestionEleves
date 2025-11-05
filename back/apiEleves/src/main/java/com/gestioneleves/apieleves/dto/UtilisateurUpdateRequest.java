package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Payload pour la mise à jour partielle d'un utilisateur.
 * Tous les champs sont optionnels.
 */
public class UtilisateurUpdateRequest {
    public String nom;
    public String prenom;
    @Email
    public String email;
    @Size(min = 8)
    public String motDePasse;
    @Past
    public LocalDate dateNaissance;
    @Pattern(regexp = "^[0-9+ .-]{8,20}$")
    public String numTel;
}

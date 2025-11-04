package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

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
    @NotNull
    public Date dateNaissance;
    @NotBlank
    public String numTel;
    public Role role;
}

package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;

import java.util.Date;

/**
 * Représentation publique d'un utilisateur à exposer par l'API.
 * Ne contient jamais le mot de passe.
 */
public class UtilisateurDTO {
    public Long idUtilisateur;
    public String nom;
    public String prenom;
    public String email;
    public String numTel;
    public Date dateNaissance;
    public Role role;
}

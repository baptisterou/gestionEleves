package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;

import java.util.Date;

/**
 * Payload pour la mise à jour partielle d'un utilisateur.
 * Tous les champs sont optionnels.
 */
public class UtilisateurUpdateRequest {
    public String nom;
    public String prenom;
    public String email;
    public String motDePasse;
    public Date dateNaissance;
    public String numTel;
    public Role role;
}

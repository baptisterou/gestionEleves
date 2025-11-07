package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;

import java.time.LocalDate;

/**
 * DTO d'administration pour l'utilisateur, inclut le role.
 */
public class UtilisateurAdminDTO {
    public Long idUtilisateur;
    public String nom;
    public String prenom;
    public String email;
    public String numTel;
    public LocalDate dateNaissance;
    public Role role;
}

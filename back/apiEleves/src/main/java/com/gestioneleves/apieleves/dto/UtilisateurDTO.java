package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;
import java.time.LocalDate;

/**
 * Représentation publique d'un utilisateur à exposer par l'API.
 * Ne contient jamais le mot de passe.
 * Le champ role est renseigné uniquement pour les ADMIN (sinon null).
 */
public class UtilisateurDTO {
    public Long idUtilisateur;
    public String nom;
    public String prenom;
    public String email;
    public String numTel;
    public LocalDate dateNaissance;
    public Role role; // optionnel, visible pour ADMIN uniquement
}

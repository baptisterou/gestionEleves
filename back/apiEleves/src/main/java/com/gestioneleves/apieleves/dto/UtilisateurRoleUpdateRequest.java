package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;
import jakarta.validation.constraints.NotNull;

/**
 * Payload réservé aux administrateurs pour changer le rôle d'un utilisateur existant.
 */
public class UtilisateurRoleUpdateRequest {
    @NotNull
    public Role role;
}

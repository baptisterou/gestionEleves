package com.gestioneleves.apieleves.dto;

import lombok.Data;

@Data
public class RepresentationDTO {
    private Long idRepresentation;
    private Long idEleve;
    private Long idUtilisateur;

    // Champ calculé (test pour affichage plus pratique)
    private String nomCompletEleve;
    private String nomCompletUtilisateur;
}

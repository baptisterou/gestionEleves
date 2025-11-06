package com.gestioneleves.apieleves.dto;

import lombok.Data;

@Data
public class MatiereUpdateRequest {
    private String intituleMatiere;
    private Long idEnseignant; // optionnel
}

package com.gestioneleves.apieleves.dto;

import lombok.Data;

@Data
public class MatiereDTO {
    private Long idMatiere;
    private String intituleMatiere;
    private Long enseignantId; // optionnel
}

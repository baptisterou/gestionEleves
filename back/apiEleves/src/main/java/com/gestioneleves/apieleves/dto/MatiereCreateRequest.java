package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MatiereCreateRequest {
    @NotBlank
    private String intituleMatiere;
    // optionnel: rattacher à un enseignant existant
    private Long idEnseignant;
}

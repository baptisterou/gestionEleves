package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClasseCreateRequest {
    @NotBlank
    private String nomClasse;
    @NotBlank
    private String niveauClasse;
    @NotBlank
    private String anneeScolaire;
    // Optionnel: rattacher un enseignant
    private Long idEnseignant;
}

package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnseignementCreateRequest {
    @NotNull
    private Long idClasse;
    @NotNull
    private Long idUtilisateur;
    @NotNull
    private Long idMatiere;
}


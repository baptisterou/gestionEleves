package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParcoursCreateRequest {
    @NotNull
    private Long idEleve;
    @NotNull
    private Long idClasse;
}

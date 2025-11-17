package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NoteCreateRequest {
    @NotNull
    private LocalDate dateNote;

    @Positive(message = "Le coefficient doit être > 0")
    private float coefNote;

    @DecimalMin(value = "0.0", inclusive = true, message = "La note doit être >= 0")
    @DecimalMax(value = "20.0", inclusive = true, message = "La note doit être <= 20")
    private float valeurNote;

    @NotNull
    private Long idEleve;

    @NotNull
    private Long idMatiere;

    private Long idBulletin; // optionnel
}

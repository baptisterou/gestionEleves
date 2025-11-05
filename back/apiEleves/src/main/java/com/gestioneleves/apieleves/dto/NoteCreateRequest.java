package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class NoteCreateRequest {
    @NotNull
    private Date dateNote;

    @Positive(message = "Le coefficient doit être > 0")
    private float coefNote;

    @DecimalMin(value = "0.0", inclusive = true, message = "La note doit être >= 0")
    @DecimalMax(value = "20.0", inclusive = true, message = "La note doit être <= 20")
    private float valeurNote;

    @NotNull
    private Long eleveId;

    @NotNull
    private Long matiereId;

    private Long bulletinId; // optionnel
}

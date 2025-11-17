package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NoteUpdateRequest {
    // Tous optionnels pour un patch-style update
    private LocalDate dateNote;

    @Positive(message = "Le coefficient doit être > 0")
    private Float coefNote; // wrapper pour pouvoir être null

    @DecimalMin(value = "0.0", inclusive = true, message = "La note doit être >= 0")
    @DecimalMax(value = "20.0", inclusive = true, message = "La note doit être <= 20")
    private Float valeurNote;

    private Long idEleve;
    private Long idMatiere;
    private Long idBulletin;
}

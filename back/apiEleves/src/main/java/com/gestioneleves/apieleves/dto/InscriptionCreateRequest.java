package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InscriptionCreateRequest {
    @NotNull
    private Long eleveId;
    @NotNull
    private Long utilisateurId;

    @PastOrPresent
    private LocalDate dateInscrip; // optionnel
}

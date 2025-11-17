package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InscriptionCreateRequest {
    @NotNull
    private Long idEleve;
    @NotNull
    private Long idUtilisateur;

    @PastOrPresent
    private LocalDate dateInscrip; // optionnel
}

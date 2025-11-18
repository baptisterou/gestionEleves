package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InscriptionDTO {
    private Long idInscription;
    private LocalDate dateInscrip;
    @NotNull(message = "L'ID de l'élève est obligatoire")
    private Long idEleve;
    @NotNull(message = "L'ID de l'utilisateur est obligatoire")
    private Long idUtilisateur;
}

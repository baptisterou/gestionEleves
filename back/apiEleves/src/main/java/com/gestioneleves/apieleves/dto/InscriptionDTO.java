package com.gestioneleves.apieleves.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InscriptionDTO {
    private Long idInscription;
    private LocalDate dateInscrip;
    private Long idEleve;
    private Long idUtilisateur;
}

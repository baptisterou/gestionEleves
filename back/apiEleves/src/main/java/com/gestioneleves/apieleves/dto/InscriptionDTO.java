package com.gestioneleves.apieleves.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InscriptionDTO {
    private Long eleveId;
    private Long utilisateurId;
    private LocalDate dateInscrip;
}

package com.gestioneleves.apieleves.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EleveDTO {
    private Long idEleve;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Long utilisateurId; // responsable id (optionnel)
}

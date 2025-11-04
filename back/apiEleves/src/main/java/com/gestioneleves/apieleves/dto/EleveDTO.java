package com.gestioneleves.apieleves.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EleveDTO {
    private Long idEleve;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private Long utilisateurId; // responsable id (optionnel)
}

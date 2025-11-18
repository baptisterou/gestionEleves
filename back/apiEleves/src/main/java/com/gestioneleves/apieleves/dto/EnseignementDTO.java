package com.gestioneleves.apieleves.dto;

import lombok.Data;

@Data
public class EnseignementDTO {
    private Long idEnseignement;
    private Long idClasse;
    private Long idUtilisateur;
    private Long idMatiere;
}

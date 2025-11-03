package com.gestioneleves.apieleves.dto;

import lombok.Data;

@Data
public class ClasseUpdateRequest {
    private String nomClasse;
    private String niveauClasse;
    private String anneeScolaire;
    private Long enseignantId;
}

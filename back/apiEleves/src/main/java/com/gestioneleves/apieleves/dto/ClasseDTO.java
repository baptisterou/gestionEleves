package com.gestioneleves.apieleves.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClasseDTO {
    private Long idClasse;
    private String nomClasse;
    private String niveauClasse;
    private String anneeScolaire;
    private String nom;
    private String prenom;
}

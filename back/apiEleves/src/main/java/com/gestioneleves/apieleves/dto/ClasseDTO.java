package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Utilisateur;
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
    private Long EnseignantId;
}

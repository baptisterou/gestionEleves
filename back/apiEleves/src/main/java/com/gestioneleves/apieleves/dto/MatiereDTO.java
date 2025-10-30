package com.gestioneleves.apieleves.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatiereDTO {
    private Long idMatiere;
    private String intituleMatiere;
    private String nom;
    private String prenom;

}

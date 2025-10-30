package com.gestioneleves.apieleves.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDTO {
    protected Long idUtilisateur;
    protected String nom;
    protected String prenom;
}
package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.Past;
import lombok.Data;

import java.util.Date;

@Data
public class EleveUpdateRequest {
    private String nom;
    private String prenom;
    @Past
    private Date dateNaissance;
    private Long utilisateurId;
}

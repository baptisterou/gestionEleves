package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EleveUpdateRequest {
    private String nom;
    private String prenom;
    @Past
    private LocalDate dateNaissance;
    private Long utilisateurId;
}

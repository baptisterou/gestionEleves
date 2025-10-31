package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurGestion {
    @NotBlank private String nom;
    @NotBlank private String prenom;
    @NotBlank private String email;
    @NotBlank private String motDePasse;
    @NotNull private Date dateNaissance;
    @NotBlank private String numTel;
    @NotNull
    private Role role;
}

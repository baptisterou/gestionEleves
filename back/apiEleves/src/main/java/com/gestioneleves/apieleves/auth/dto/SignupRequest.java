package com.gestioneleves.apieleves.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @Email @NotBlank
    private String email;
    @NotBlank @Size(min = 8)
    private String password;
    @NotNull
    private LocalDate dateNaissance;
    @NotBlank
    private String numTel;
}

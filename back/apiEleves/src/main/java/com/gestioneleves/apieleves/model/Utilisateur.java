package com.gestioneleves.apieleves.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
@Table(name = "utilisateur")
@Component
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idUtilisateur")
    private Long idUtilisateur;
    @Column(name="Nom")
    private String nom;
    @Column(name="Prenom")
    private String prenom;
    @Column(name="Email")
    private String email;
    @Column(name="MotDePasse")
    private String motDePasse;
    @Column(name="DateNaissance")
    private String dateNaissance;
    @Column(name="NumTel")
    private String numTel;
    @Column(name="Role")
    private String role;
}

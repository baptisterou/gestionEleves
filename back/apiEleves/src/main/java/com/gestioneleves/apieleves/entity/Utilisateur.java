package com.gestioneleves.apieleves.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_utilisateur")
    private Long idUtilisateur;
    @Column(name="nom")
    private String nom;
    @Column(name="prenom")
    private String prenom;
    @Column(name="email")
    private String email;
    @Column(name="mot_de_passe")
    private String motDePasse;
    @Column(name="date_naissance")
    private String dateNaissance;
    @Column(name="num_tel")
    private String numTel;
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private List<Eleve> eleves = new ArrayList<>();

    @OneToMany(mappedBy = "enseignant")
    @JsonIgnore
    private List<Classe> classesEnseignant = new ArrayList<>();

    @OneToMany(mappedBy = "enseignant")
    @JsonIgnore
    private List<Matiere> matieresEnseignant = new ArrayList<>();

    @OneToMany(mappedBy = "utilisateur")
    private List<Inscrire> inscriptionEffectuees = new ArrayList<>();

}

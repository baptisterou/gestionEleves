package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "utilisateur")
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
    @Enumerated(EnumType.ORDINAL)
    @Column(name="Role")
    private Role role;

    @OneToMany(mappedBy = "utilisateur")
    private List<Eleve> eleves = new ArrayList<>();

    @OneToMany(mappedBy = "utilisateur")
    private List<Classe> classesEnseignant = new ArrayList<>();

    @OneToMany(mappedBy = "utilisateur")
    private List<Matiere> matieresEnseignant = new ArrayList<>();

}

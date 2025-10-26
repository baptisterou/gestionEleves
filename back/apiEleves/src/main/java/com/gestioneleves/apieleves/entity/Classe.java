package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classe")
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idClasse;

    private String nomClasse;
    private String niveauClasse;
    private Date anneeScolaire;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur enseignant;
}

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
    private long id_classe;

    private String nom_classe;
    private String niveau_classe;
    private Date annee_scolaire;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur enseignant;
}

package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Entité représentant une Classe scolaire.
 * Une classe contient des informations générales (nom, niveau, année) et
 * peut être liée à plusieurs enseignements et parcours.
 */
//Même annotations que l'entité Bulletin, s'y référencé pour des détails
@Getter
@Setter
@ToString(exclude = {"enseignements"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classe")
public class Classe {

    @Id //Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément géré par la base
    @EqualsAndHashCode.Include
    private Long idClasse;

    private String nomClasse;      // Nom de la classe, ex : "6ème A"
    private String niveauClasse;   // Niveau de la classe, ex : "6ème"
    private String anneeScolaire;  // Année scolaire, ex : "2025-2026"

    /**
     * Liste des enseignements associés à cette classe.
     * Relation One-to-Many : une classe peut avoir plusieurs enseignements.
     * mappedBy = "classe" : la clé étrangère se trouve dans l'entité Enseignement.
     */
    @OneToMany(mappedBy = "classe")
    private List<Enseignement> enseignements;

    /**
     * Liste des parcours associés à cette classe.
     * Relation One-to-Many : une classe peut avoir plusieurs parcours.
     * mappedBy = "classe" : la clé étrangère se trouve dans l'entité Parcours.
     */
    @OneToMany(mappedBy = "classe")
    private List<Parcours> parcours;
}

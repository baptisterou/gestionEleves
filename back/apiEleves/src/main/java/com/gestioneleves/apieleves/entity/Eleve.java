package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

/**
 * Entité JPA représentant un élève dans le système
 * Correspond à la table "eleve" en base de données
 */
//Même annotations que l'entité Bulletin, s'y référencé pour des détails
@Getter
@Setter
@ToString(exclude = {"utilisateur", "inscriptions", "notes"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indique que cette classe est une entité JPA
@Table(name = "eleve")
public class Eleve {
    
    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    @EqualsAndHashCode.Include
    private Long idEleve;

    private String nom;           // Nom de l'élève
    private String prenom;        // Prénom de l'élève
    private LocalDate dateNaissance; // Date de naissance de l'élève

    /**
     * Liste des inscriptions de l'élève.
     * Relation One-to-Many : un élève peut avoir plusieurs inscriptions à des classes ou activités.
     */
    @OneToMany(mappedBy = "eleve")
    private List<Inscription> inscriptions = new ArrayList<>();

    /**
     * Liste des notes de l'élève.
     * Relation One-to-Many : un élève peut avoir plusieurs notes dans différents bulletins/matières.
     */
    @OneToMany(mappedBy = "eleve")
    private List<Note> notes = new ArrayList<>();

    /**
     * Liste des représentants des élèves.
     * Relation One-to-Many : un élève peut être associé à plusieurs représentants.
     */
    @OneToMany(mappedBy = "eleve")
    private List<Representation> representations = new ArrayList<>();

    /**
     * Liste des parcours de l'élève (ex : parcours scolaire ou pédagogique).
     * Relation One-to-Many : un élève peut avoir plusieurs parcours.
     */
    @OneToMany(mappedBy = "eleve")
    private List<Parcours> parcours = new ArrayList<>();
}

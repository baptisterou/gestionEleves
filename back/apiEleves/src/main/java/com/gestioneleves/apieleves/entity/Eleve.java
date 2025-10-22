package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entité JPA représentant un élève dans le système
 * Correspond à la table "eleve" en base de données
 */
@Data // Annotation Lombok qui génère automatiquement getters, setters, toString, equals, hashCode
@Entity // Indique que cette classe est une entité JPA
@Table(name = "eleve") // Spécifie le nom de la table en base de données
public class Eleve {

    /**
     * Identifiant unique de l'élève
     * Clé primaire auto-générée par la base de données
     */
    @Id // Marque ce champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    private long id_eleve;

    /**
     * Nom de famille de l'élève
     */
    private String nom_eleve;

    /**
     * Prénom de l'élève
     */
    private String prenom_eleve;

    /**
     * Date de naissance de l'élève
     */
    private Date naissance_eleve;

    /**
     * Relation One-to-Many avec l'entité Bulletin
     * Un élève peut avoir plusieurs bulletins (un par trimestre)
     * mappedBy = "eleve" indique que la relation est gérée par l'attribut "eleve" dans Bulletin
     */
    @OneToMany(mappedBy = "eleve")
    private List<Note> notes = new ArrayList<>();

}

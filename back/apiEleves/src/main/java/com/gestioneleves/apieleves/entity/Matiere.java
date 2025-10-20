package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entité JPA représentant une matière dans le système
 * Correspond à la table "matiere" en base de données
 */
@Data // Annotation Lombok qui génère automatiquement getters, setters, toString, equals, hashCode
@Entity // Indique que cette classe est une entité JPA
@Table(name = "matiere") // Spécifie le nom de la table en base de données
public class Matiere {

    /**
     * Identifiant unique de la matière
     * Clé primaire auto-générée par la base de données
     */
    @Id // Marque ce champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    private long id_matiere;

    /**
     * Intitulé ou nom de la matière (ex: "Mathématiques", "Français")
     */
    private String intitule_matiere;

    /**
     * Constructeur par défaut requis par JPA
     * Utilisé par Hibernate pour instancier les entités
     */
    public Matiere() {
        // Constructeur vide requis par JPA
    }

    /**
     * Constructeur avec paramètres pour créer une nouvelle matière
     * @param intitule_matiere Le nom de la matière à créer
     */
    public Matiere(String intitule_matiere) {
        this.intitule_matiere = intitule_matiere;
    }
}
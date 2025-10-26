package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité JPA représentant une matière dans le système
 * Correspond à la table "matiere" en base de données
 */
@Data // Annotation Lombok qui génère automatiquement getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indique que cette classe est une entité JPA
@Table(name = "matiere") // Spécifie le nom de la table en base de données
public class Matiere {

    /**
     * Identifiant unique de la matière
     * Clé primaire auto-générée par la base de données
     */
    @Id // Marque ce champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    private long idMatiere;

    /**
     * Intitulé ou nom de la matière (ex: "Mathématiques", "Français")
     */
    private String intituleMatiere;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur enseignant;
}

package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.ArrayList;

/**
 * Entité JPA représentant un bulletin scolaire dans le système
 * Correspond à la table "bulletin" en base de données
 */
@Data // Annotation Lombok qui génère automatiquement getters, setters, toString, equals, hashCode
@Entity // Indique que cette classe est une entité JPA
@Table(name = "bulletin") // Spécifie le nom de la table en base de données
public class Bulletin {

    /**
     * Identifiant unique du bulletin
     * Clé primaire auto-générée par la base de données
     */
    @Id // Marque ce champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    private long id_bulletin;

    /**
     * Numéro du trimestre (ex: 1, 2 ou 3)
     */
    private int trimestre_bulletin;
    /**
     * Année scolaire du bulletin (ex: 2024)
     */
    private int annee_bulletin;

    private String commentaire;


    @OneToMany(mappedBy = "note")
    private List<Note> notes = new ArrayList<>();


}

package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entité JPA représentant un élève dans le système
 * Correspond à la table "eleve" en base de données
 */
@Data // Annotation Lombok qui génère automatiquement getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indique que cette classe est une entité JPA
@Table(name = "eleve")
public class Eleve {
    
    @Id // Marque ce champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    private long id_eleve;
    private String nom_eleve;
    private String prenom_eleve;
    private Date naissance_eleve;

    /**
     * Relation One-to-Many avec l'entité Bulletin
     * Un élève peut avoir plusieurs bulletins (un par trimestre)
     * mappedBy = "eleve" indique que la relation est gérée par l'attribut "eleve" dans Bulletin
     */
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "eleve")
    private List<Inscrire> inscriptions = new ArrayList<>();

}

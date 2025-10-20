package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    /**
     * Relation Many-to-One avec l'entité Eleve
     * Plusieurs bulletins peuvent appartenir à un même élève
     * nullable=false : un bulletin doit toujours être associé à un élève
     */
    @ManyToOne
    @JoinColumn(name = "id_eleve", nullable = false) // Clé étrangère vers la table eleve
    private Eleve eleve;

    /**
     * Constructeur par défaut requis par JPA
     * Utilisé par Hibernate pour instancier les entités
     */
    public Bulletin() {
        // Constructeur vide requis par JPA
    }

    /**
     * Constructeur avec paramètres pour créer un nouveau bulletin
     * @param trimestre_bulletin Le numéro du trimestre
     * @param annee_bulletin L'année scolaire
     * @param eleve L'élève auquel ce bulletin est associé
     */
    public Bulletin(int trimestre_bulletin, int annee_bulletin, Eleve eleve) {
        this.trimestre_bulletin = trimestre_bulletin;
        this.annee_bulletin = annee_bulletin;
        this.eleve = eleve;
    }
}
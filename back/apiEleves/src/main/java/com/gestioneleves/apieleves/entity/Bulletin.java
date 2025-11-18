package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un Bulletin scolaire.
 * Un bulletin correspond à un trimestre d'un élève et peut contenir plusieurs notes.
 */
@Getter // Génère automatiquement tous les getters
@Setter // Génère automatiquement tous les setters
@ToString(exclude = {"notes"}) // Exclut la liste "notes" du toString pour éviter récursion infinie
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Seuls les champs annotés seront utilisés dans equals/hashCode
@NoArgsConstructor // Génère un constructeur vide
@AllArgsConstructor // Génère un constructeur avec tous les champs
@Entity // Déclare que cette classe est une entité JPA
@Table(name = "bulletin") // Spécifie le nom de la table en base de données
public class Bulletin {

    @Id // Clé primaire de la table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément géré par la base
    @EqualsAndHashCode.Include // Champ utilisé pour equals() et hashCode()
    private Long idBulletin;

    private int trimestreBulletin; // Trimestre du bulletin (1, 2 ou 3)
    private int anneeBulletin;     // Année scolaire correspondante
    private String commentaire;    // Commentaire général du bulletin

    /**
     * Liste des notes associées à ce bulletin.
     * Relation One-to-Many : un bulletin peut avoir plusieurs notes.
     * mappedBy = "bulletin" : la clé étrangère se trouve dans la classe Note
     */
    @OneToMany(mappedBy = "bulletin")
    private List<Note> notes = new ArrayList<>();
}

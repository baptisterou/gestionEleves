package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité JPA représentant une matière dans le système
 * Correspond à la table "matiere" en base de données
 */
@Getter
@Setter
@ToString(exclude = {"enseignant", "notes"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    @EqualsAndHashCode.Include
    private Long idMatiere;

    /**
     * Intitulé ou nom de la matière (ex: "Mathématiques", "Français")
     */
    private String intituleMatiere;

    @OneToMany(mappedBy = "matiere")
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "matiere")
    private List<Enseignement> enseignements = new ArrayList<>();
}

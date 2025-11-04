package com.gestioneleves.apieleves.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entité JPA représentant un élève dans le système
 * Correspond à la table "eleve" en base de données
 */
@Getter
@Setter
@ToString(exclude = {"utilisateur", "inscriptions", "notes"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Indique que cette classe est une entité JPA
@Table(name = "eleve")
public class Eleve {
    
    @Id // Marque ce champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    @EqualsAndHashCode.Include
    private Long idEleve;
    private String nom;
    private String prenom;
    private Date dateNaissance;

    /**
     * Relation One-to-Many avec l'entité Bulletin
     * Un élève peut avoir plusieurs bulletins (un par trimestre)
     * mappedBy = "eleve" indique que la relation est gérée par l'attribut "eleve" dans Bulletin
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utilisateur")
    //@JsonManagedReference
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "eleve")
    @JsonIgnore
    private List<Inscrire> inscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "eleve")
    @JsonIgnore
    private List<Note> notes = new ArrayList<>();

}

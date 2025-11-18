package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité représentant l'association entre une Classe, une Matière et un Enseignant.
 * Chaque Enseignement correspond à une matière enseignée dans une classe par un enseignant.
 */
//Même annotations que l'entité Bulletin, s'y référencé pour des détails
@Entity
@Getter
@Setter
@ToString(exclude = {"matiere", "classe", "enseignant"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Enseignement {
    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base
    @EqualsAndHashCode.Include
    private Long idEnseignement;

    /**
     * Matière enseignée.
     * Relation Many-to-One : plusieurs enseignements peuvent être de la même matière.
     */
    @ManyToOne
    @JoinColumn(name = "id_matiere")
    private Matiere matiere;

    /**
     * Classe concernée par cet enseignement.
     * Relation Many-to-One : plusieurs enseignements peuvent appartenir à la même classe.
     */
    @ManyToOne
    @JoinColumn(name = "id_classe")
    private Classe classe;

    /**
     * Enseignant responsable de cet enseignement.
     * Relation Many-to-One : un enseignant peut avoir plusieurs enseignements.
     */
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur enseignant;
}

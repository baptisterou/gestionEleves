package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entité représentant une inscription d'un élève.
 * Une inscription correspond à l'enregistrement d'un élève dans le système à une date donnée,
 * et peut être associée à un admin.
 */
//Même annotations que l'entité Bulletin, s'y référencé pour des détails
@Entity
@Table(name = "inscription")
@Getter
@Setter
@ToString(exclude = {"eleve", "utilisateur"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Inscription {

    @Id // Marque ce champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    @EqualsAndHashCode.Include
    @Column(name = "id_inscription") // Nom exact de la colonne en base
    private Long idInscription;

    @Column(name = "DateInscrip")
    private LocalDate dateInscrip;

    /**
     * Élève concerné par l'inscription.
     * Relation Many-to-One : plusieurs inscriptions peuvent appartenir au même élève.
     */
    @ManyToOne
    @JoinColumn(name = "id_eleve") // Colonne FK vers Eleve
    private Eleve eleve;

    /**
     * UAdmin ayant effectué l'inscription.
     * Relation Many-to-One : plusieurs inscriptions peuvent être enregistrées par le même utilisateur.
     */
    @ManyToOne
    @JoinColumn(name = "id_utilisateur") // Colonne FK vers Utilisateur
    private Utilisateur utilisateur;


}

package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "representation")
@Getter
@Setter
@ToString(exclude = {"eleve", "utilisateur"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Representation {

    @Id // Marque ce champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    @EqualsAndHashCode.Include
    @Column(name = "id_representation")
    private Long idRepresentation;

    @ManyToOne
    @JoinColumn(name = "id_eleve")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
}

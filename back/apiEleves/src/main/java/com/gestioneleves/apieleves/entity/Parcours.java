package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parcours")
@Getter
@Setter
@ToString(exclude = {"eleve", "classe"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Parcours {

    @Id // Marque ce champ comme clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément par la base de données
    @EqualsAndHashCode.Include
    @Column(name = "id_parcours")
    private Long idParcours;

    @ManyToOne
    @JoinColumn(name = "id_eleve")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "id_classe")
    private Classe classe;
}

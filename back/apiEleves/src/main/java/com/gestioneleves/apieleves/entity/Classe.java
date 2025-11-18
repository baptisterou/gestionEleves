package com.gestioneleves.apieleves.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(exclude = {"enseignements"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classe")
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idClasse;

    private String nomClasse;
    private String niveauClasse;
    private String anneeScolaire;

    @OneToMany(mappedBy = "classe")
    private List<Enseignement> enseignements;

    @OneToMany(mappedBy = "classe")
    private List<Parcours> parcours;
}

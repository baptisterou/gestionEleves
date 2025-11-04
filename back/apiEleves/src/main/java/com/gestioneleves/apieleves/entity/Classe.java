package com.gestioneleves.apieleves.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"enseignant"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classe")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idClasse;

    private String nomClasse;
    private String niveauClasse;
    private String anneeScolaire;

    @ManyToOne
    @JoinColumn(name = "id_enseignant")
    private Utilisateur enseignant;
}

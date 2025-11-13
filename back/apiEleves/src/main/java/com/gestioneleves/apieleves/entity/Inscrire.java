package com.gestioneleves.apieleves.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "inscrire")
@Getter
@Setter
@ToString(exclude = {"eleve", "utilisateur"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Inscrire implements Serializable {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private InscrireId id;

    private List<String> inscriptions;

    @ManyToOne
    @MapsId("idEleve")
    @JoinColumn(name = "id_eleve")
    private Eleve eleve;

    @ManyToOne
    @MapsId("idUtilisateur")
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @Column(name = "DateInscrip")
    private LocalDate dateInscrip;
}

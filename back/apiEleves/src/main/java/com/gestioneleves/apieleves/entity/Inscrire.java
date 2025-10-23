package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "inscrire")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscrire implements Serializable {

    @EmbeddedId
    private InscrireId id;

    @ManyToOne
    @MapsId("id_eleve")
    @JoinColumn(name = "id_eleve")
    private Eleve eleve;

    @ManyToOne
    @MapsId("id_utilisateur")
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @Column(name = "DateInscrip")
    private LocalDate dateInscrip;
}

package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_note;

    @Temporal(TemporalType.DATE)
    private Date date_note;
    private float coef_note;
    private float valeur_note;

    @ManyToOne
    @JoinColumn(name = "id_eleve")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "id_matiere")
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "id_bulletin")
    private Bulletin bulletin;
}

package com.gestioneleves.apieleves.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_note;

    private Date date_note;
    private float coef_note;

    @OneToMany(mappedBy = id_eleve)
    private List<Eleve> eleves;

    @OneToMany(mappedBy = id_matiere)
    private List<Matiere> matieres;
}

package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idNote;

    @Temporal(TemporalType.DATE)
    private Date dateNote;
    private float coefNote;
    private float valeurNote;

    @ManyToOne
    @JoinColumn(name = "idEleve")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "idMatiere")
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "id_bulletin")
    private Bulletin bulletin;
}

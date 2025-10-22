package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bulletin")
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_bulletin;

    private int trimestre_bulletin;
    private int annee_bulletin;
    private String commentaire;

    @OneToMany(mappedBy = "note")
    private List<Note> notes = new ArrayList<>();


}

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
    private Long idBulletin;

    private int trimestreBulletin;
    private int anneeBulletin;
    private String commentaire;


    @OneToMany(mappedBy = "bulletin")
    private List<Note> notes = new ArrayList<>();


}

package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "matiere")
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_matiere;

    private String intitule_matiere;

    public Matiere() {
        // JPA
    }

    public Matiere(String intitule_matiere) {
        this.intitule_matiere = intitule_matiere;
    }
}

package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "eleve")
public class Eleve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_eleve;

    private String nom_eleve;
    private String prenom_eleve;
    private Date naissance_eleve;

    @OneToMany(mappedBy = "eleve")
    private List<Bulletin> bulletins = new ArrayList<>();

    public Eleve() {
        // JPA
    }

    public Eleve(String nom_eleve, String prenom_eleve, Date naissance_eleve) {
        this.nom_eleve = nom_eleve;
        this.prenom_eleve = prenom_eleve;
        this.naissance_eleve = naissance_eleve;
    }
}

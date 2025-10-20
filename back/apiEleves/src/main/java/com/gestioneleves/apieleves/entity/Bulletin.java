package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bulletin")
public class Bulletin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_bulletin;

    private int trimestre_bulletin;
    private int annee_bulletin;

    @ManyToOne
    @JoinColumn(name = "id_eleve", nullable=false)
    private Eleve eleve;


    public Bulletin() {
        // JPA
    }

    public Bulletin(int trimestre_bulletin, int annee_bulletin, Eleve eleve) {
        this.trimestre_bulletin = trimestre_bulletin;
        this.annee_bulletin = annee_bulletin;
        this.eleve = eleve;
    }
}

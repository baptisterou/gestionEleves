package com.gestioneleves.apieleves.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"notes"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bulletin")
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idBulletin;

    private int trimestreBulletin;
    private int anneeBulletin;
    private String commentaire;

    @OneToMany(mappedBy = "bulletin")
    //@JsonIgnore
    private List<Note> notes = new ArrayList<>();
}

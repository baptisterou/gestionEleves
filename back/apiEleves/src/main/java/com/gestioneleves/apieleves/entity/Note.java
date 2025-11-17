package com.gestioneleves.apieleves.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(exclude = {"eleve", "matiere", "bulletin"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idNote;

    private LocalDate dateNote;
    private float coefNote;
    private float valeurNote;

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

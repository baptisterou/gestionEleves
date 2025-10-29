package com.gestioneleves.apieleves.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@ToString(exclude = {"notes"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bulletin")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idBulletin;

    private int trimestreBulletin;
    private int anneeBulletin;
    private String commentaire;

    @OneToMany(mappedBy = "bulletin")
    @JsonIgnore
    private List<Note> notes = new ArrayList<>();
}

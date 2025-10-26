package com.gestioneleves.apieleves.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscrireId implements Serializable {
    private Integer idEleve;
    private Integer idUtilisateur;
}

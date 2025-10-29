package com.gestioneleves.apieleves.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulletinDTO {
    private Long idBulletin;
    private String nom;
    private String prenom;
    private Integer trimestreBulletin;
    private Integer anneeBulletin;
    private String commentaire;
}

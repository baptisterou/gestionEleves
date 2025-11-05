package com.gestioneleves.apieleves.dto;

import lombok.Data;

@Data
public class BulletinDTO {
    private Long idBulletin;
    private int trimestreBulletin;
    private int anneeBulletin;
    private String commentaire;
}

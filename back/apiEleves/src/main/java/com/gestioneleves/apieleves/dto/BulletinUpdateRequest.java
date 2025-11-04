package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BulletinUpdateRequest {
    @Min(1)
    @Max(3)
    private Integer trimestreBulletin; // optionnel

    private Integer anneeBulletin; // validation métier dans le service

    private String commentaire;
}

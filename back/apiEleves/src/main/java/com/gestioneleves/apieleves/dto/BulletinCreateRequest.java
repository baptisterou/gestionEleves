package com.gestioneleves.apieleves.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BulletinCreateRequest {
    @Min(1)
    @Max(3)
    private int trimestreBulletin;

    @NotNull
    private Integer anneeBulletin; // la validation métier complète est dans le service

    private String commentaire;
}

package com.gestioneleves.apieleves.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NoteDTO {
    private Long idNote;
    private Date dateNote;
    private float coefNote;
    private float valeurNote;
    private Long eleveId;
    private Long matiereId;
    private Long bulletinId;
}

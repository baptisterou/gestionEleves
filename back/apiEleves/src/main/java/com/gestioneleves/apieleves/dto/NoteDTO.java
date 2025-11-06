package com.gestioneleves.apieleves.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NoteDTO {
    private Long idNote;
    private LocalDate dateNote;
    private float coefNote;
    private float valeurNote;
    private Long eleveId;
    private Long matiereId;
    private Long bulletinId;
}

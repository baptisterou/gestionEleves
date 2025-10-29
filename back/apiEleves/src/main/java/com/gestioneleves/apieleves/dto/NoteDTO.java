package com.gestioneleves.apieleves.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private Long idNote;
    private Date dateNote;
    private Float valeurNote;
    private Float coefNote;
    private String matiere;
    private String eleveNom;
}
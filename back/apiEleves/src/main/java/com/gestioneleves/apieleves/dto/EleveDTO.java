package com.gestioneleves.apieleves.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EleveDTO {
    private Long idEleve;
    private String nomEleve;
    private String prenomEleve;
    private String naissanceEleve;
}

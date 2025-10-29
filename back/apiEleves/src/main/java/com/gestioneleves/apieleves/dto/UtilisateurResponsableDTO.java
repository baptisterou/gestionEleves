package com.gestioneleves.apieleves.dto;

import java.util.List;

public class UtilisateurResponsableDTO extends UtilisateurDTO {
    protected String email;
    protected String dateNaissance;
    protected String numTel;
    protected EleveDTO eleve;
    protected List<NoteDTO> notesEleve;
    protected List<BulletinDTO> bulletinsEleve;
}
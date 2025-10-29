package com.gestioneleves.apieleves.dto;

import java.util.List;

public class UtilisateurAdminDTO extends UtilisateurDTO {
    protected Role role;
    protected String motDePasse;
    protected List<UtilisateurDTO> utilisateurs;
    protected List<EleveDTO> eleves;
    protected List<NoteDTO> notes;
}
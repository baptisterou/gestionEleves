package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;

public class UtilisateurDTO {
    protected Long idUtilisateur;
    protected String nom;
    protected String prenom;
}

class UtilisateurEnseignantDTO extends UtilisateurDTO {

}

class UtilisateurResponsableDTO extends UtilisateurEnseignantDTO {
    protected String email;
    protected String dateNaissance;
    protected String numTel;
}

class UtilisateurAdminDTO extends UtilisateurResponsableDTO {
    protected Role role;
}
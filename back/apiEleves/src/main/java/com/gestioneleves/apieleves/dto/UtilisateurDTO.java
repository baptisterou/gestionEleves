public class UtilisateurDTO {
    protected Long idUtilisateur;
    protected String nom;
    protected String prenom;
}

class UtilisateurEnseignantDTO extends UtilisateurDTO {
    protected List<ClasseDTO> classesEnseignees;     // Toutes ses classes
    protected List<MatiereDTO> matieresEnseignees;   // Toutes ses matières
    protected List<EleveDTO> eleves;                 // Tous les élèves de ses classes
    protected List<NoteDTO> notes;                   // Notes de tous ses élèves
}

class UtilisateurResponsableDTO extends UtilisateurDTO {
    protected String email;
    protected String dateNaissance;
    protected String numTel;
    protected EleveDTO eleve;                       // SON enfant seulement
    protected List<NoteDTO> notesEleve;             // Notes de SON enfant
    protected List<BulletinDTO> bulletinsEleve;     // Bulletins de SON enfant
}

class UtilisateurAdminDTO extends UtilisateurDTO {
    protected Role role;
    protected String motDePasse;
    protected List<UtilisateurDTO> tousLesUtilisateurs;
    protected List<EleveDTO> eleves;
    protected List<NoteDTO> notes;
}
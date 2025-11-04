package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.ClasseCreateRequest;
import com.gestioneleves.apieleves.dto.ClasseDTO;
import com.gestioneleves.apieleves.dto.ClasseUpdateRequest;
import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.entity.Utilisateur;

public class ClasseMapper {

    public static Classe fromCreate(ClasseCreateRequest req) {
        Classe c = new Classe();
        c.setNomClasse(req.getNomClasse());
        c.setNiveauClasse(req.getNiveauClasse());
        c.setAnneeScolaire(req.getAnneeScolaire());
        if (req.getEnseignantId() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getEnseignantId());
            c.setEnseignant(u);
        }
        return c;
    }

    public static Classe applyUpdate(Classe target, ClasseUpdateRequest req) {
        if (req.getNomClasse() != null) target.setNomClasse(req.getNomClasse());
        if (req.getNiveauClasse() != null) target.setNiveauClasse(req.getNiveauClasse());
        if (req.getAnneeScolaire() != null) target.setAnneeScolaire(req.getAnneeScolaire());
        if (req.getEnseignantId() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getEnseignantId());
            target.setEnseignant(u);
        }
        return target;
    }

    public static ClasseDTO toDto(Classe c) {
        ClasseDTO dto = new ClasseDTO();
        dto.setIdClasse(c.getIdClasse());
        dto.setNomClasse(c.getNomClasse());
        dto.setNiveauClasse(c.getNiveauClasse());
        dto.setAnneeScolaire(c.getAnneeScolaire());
        if (c.getEnseignant() != null) dto.setEnseignantId(c.getEnseignant().getIdUtilisateur());
        return dto;
    }
}

package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.EnseignementCreateRequest;
import com.gestioneleves.apieleves.dto.EnseignementDTO;
import com.gestioneleves.apieleves.entity.*;

public class EnseignementMapper {

    public static Enseignement fromCreate(EnseignementCreateRequest req) {
        Enseignement e = new Enseignement();
        if (req.getIdUtilisateur() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdUtilisateur());
            e.setEnseignant(u);
        }
        if (req.getIdClasse() != null) {
            Classe c = new Classe();
            c.setIdClasse(req.getIdClasse());
            e.setClasse(c);
        }
        if (req.getIdMatiere() != null) {
            Matiere m = new Matiere();
            m.setIdMatiere(req.getIdMatiere());
            e.setMatiere(m);
        }
        return e;
    }

    public static Enseignement applyUpdate(Enseignement e, EnseignementDTO req) {
        if (req.getIdUtilisateur() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdUtilisateur());
            e.setEnseignant(u);
        }
        if (req.getIdClasse() != null) {
            Classe c = new Classe();
            c.setIdClasse(req.getIdClasse());
            e.setClasse(c);
        }
        if (req.getIdMatiere() != null) {
            Matiere m = new Matiere();
            m.setIdMatiere(req.getIdMatiere());
            e.setMatiere(m);
        }
        return e;
    }

    public static EnseignementDTO toDto(Enseignement e) {
        EnseignementDTO dto = new EnseignementDTO();
        dto.setIdEnseignement(e.getIdEnseignement());
        if (e.getEnseignant() != null) {
            dto.setIdUtilisateur(e.getEnseignant().getIdUtilisateur());
        }
        if (e.getClasse()!= null) {
            dto.setIdClasse(e.getClasse().getIdClasse());
        }
        if (e.getMatiere() != null) {
            dto.setIdMatiere(e.getMatiere().getIdMatiere());
        }
        return dto;
    }
}

package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.EleveCreateRequest;
import com.gestioneleves.apieleves.dto.EleveDTO;
import com.gestioneleves.apieleves.dto.EleveUpdateRequest;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Utilisateur;

import java.time.LocalDate;

public class EleveMapper {

    public static Eleve fromCreate(EleveCreateRequest req) {
        Eleve e = new Eleve();
        e.setNom(req.getNom());
        e.setPrenom(req.getPrenom());
        e.setDateNaissance(req.getDateNaissance());
        if (req.getIdUtilisateur() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdUtilisateur());
            e.setUtilisateur(u);
        }
        return e;
    }

    public static Eleve applyUpdate(Eleve target, EleveUpdateRequest req) {
        if (req.getNom() != null) target.setNom(req.getNom());
        if (req.getPrenom() != null) target.setPrenom(req.getPrenom());
        if (req.getDateNaissance() != null) target.setDateNaissance(req.getDateNaissance());
        if (req.getIdUtilisateur() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdUtilisateur());
            target.setUtilisateur(u);
        }
        return target;
    }

    public static EleveDTO toDto(Eleve e) {
        EleveDTO dto = new EleveDTO();
        dto.setIdEleve(e.getIdEleve());
        dto.setNom(e.getNom());
        dto.setPrenom(e.getPrenom());
        dto.setDateNaissance(e.getDateNaissance());
        if (e.getUtilisateur() != null) {
            dto.setIdUtilisateur(e.getUtilisateur().getIdUtilisateur());
        }
        return dto;
    }
}

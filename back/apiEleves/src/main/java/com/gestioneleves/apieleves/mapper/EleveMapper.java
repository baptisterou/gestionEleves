package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.EleveCreateRequest;
import com.gestioneleves.apieleves.dto.EleveDTO;
import com.gestioneleves.apieleves.dto.EleveUpdateRequest;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Utilisateur;

import java.time.LocalDate;
import java.util.Date;

public class EleveMapper {

    private static LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private static Date toDate(LocalDate localDate) {
        if (localDate == null) return null;
        return java.sql.Date.valueOf(localDate);
    }

    public static Eleve fromCreate(EleveCreateRequest req) {
        Eleve e = new Eleve();
        e.setNom(req.getNom());
        e.setPrenom(req.getPrenom());
        e.setDateNaissance(toDate(req.getDateNaissance()));
        if (req.getUtilisateurId() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getUtilisateurId());
            e.setUtilisateur(u);
        }
        return e;
    }

    public static Eleve applyUpdate(Eleve target, EleveUpdateRequest req) {
        if (req.getNom() != null) target.setNom(req.getNom());
        if (req.getPrenom() != null) target.setPrenom(req.getPrenom());
        if (req.getDateNaissance() != null) target.setDateNaissance(toDate(req.getDateNaissance()));
        if (req.getUtilisateurId() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getUtilisateurId());
            target.setUtilisateur(u);
        }
        return target;
    }

    public static EleveDTO toDto(Eleve e) {
        EleveDTO dto = new EleveDTO();
        dto.setIdEleve(e.getIdEleve());
        dto.setNom(e.getNom());
        dto.setPrenom(e.getPrenom());
        dto.setDateNaissance(toLocalDate(e.getDateNaissance()));
        if (e.getUtilisateur() != null) {
            dto.setUtilisateurId(e.getUtilisateur().getIdUtilisateur());
        }
        return dto;
    }
}

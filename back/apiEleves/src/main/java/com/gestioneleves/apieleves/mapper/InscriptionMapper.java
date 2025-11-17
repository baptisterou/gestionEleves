package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.InscriptionCreateRequest;
import com.gestioneleves.apieleves.dto.InscriptionDTO;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Inscription;
import com.gestioneleves.apieleves.entity.Utilisateur;

public class InscriptionMapper {

    public static Inscription fromCreate(InscriptionCreateRequest req) {
        Inscription i = new Inscription();
        i.setDateInscrip(req.getDateInscrip());
        if (req.getIdUtilisateur() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdUtilisateur());
            i.setUtilisateur(u);
        }
        if (req.getIdEleve() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getIdEleve());
            i.setEleve(e);
        }
        return i;
    }

    public static Inscription applyUpdate(Inscription i, InscriptionDTO req) {
        if (req.getDateInscrip() != null) i.setDateInscrip(req.getDateInscrip());
        if (req.getIdUtilisateur() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdUtilisateur());
            i.setUtilisateur(u);
        }
        if (req.getIdEleve() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getIdEleve());
            i.setEleve(e);
        }
        return i;
    }

    public static InscriptionDTO toDto(Inscription i) {
        InscriptionDTO dto = new InscriptionDTO();
        dto.setIdInscription(i.getIdInscription());
        dto.setDateInscrip(i.getDateInscrip());
        if (i.getUtilisateur() != null) {
            dto.setIdUtilisateur(i.getUtilisateur().getIdUtilisateur());
        }
        if (i.getEleve() != null) {
            dto.setIdEleve(i.getEleve().getIdEleve());
        }
        return dto;
    }
}

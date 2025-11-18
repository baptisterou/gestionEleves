package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.InscriptionCreateRequest;
import com.gestioneleves.apieleves.dto.InscriptionDTO;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Inscrire;
import com.gestioneleves.apieleves.entity.InscrireId;
import com.gestioneleves.apieleves.entity.Utilisateur;

public class InscrireMapper {

    public static Inscrire fromCreate(InscriptionCreateRequest req) {
        Inscrire entity = new Inscrire();
        InscrireId id = new InscrireId(req.getEleveId(), req.getUtilisateurId());
        entity.setId(id);
        // set associations by id only
        Eleve e = new Eleve();
        e.setIdEleve(req.getEleveId());
        entity.setEleve(e);
        Utilisateur u = new Utilisateur();
        u.setIdUtilisateur(req.getUtilisateurId());
        entity.setUtilisateur(u);
        entity.setDateInscrip(req.getDateInscrip());
        return entity;
    }

    public static InscriptionDTO toDto(Inscrire e) {
        InscriptionDTO dto = new InscriptionDTO();
        if (e.getId() != null) {
            dto.setEleveId(e.getId().getIdEleve());
            dto.setUtilisateurId(e.getId().getIdUtilisateur());
        } else {
            if (e.getEleve() != null) dto.setEleveId(e.getEleve().getIdEleve());
            if (e.getUtilisateur() != null) dto.setUtilisateurId(e.getUtilisateur().getIdUtilisateur());
        }
        dto.setDateInscrip(e.getDateInscrip());
        return dto;
    }
}

package com.gestioneleves.apieleves.mapper;


import com.gestioneleves.apieleves.dto.RepresentationCreateRequest;
import com.gestioneleves.apieleves.dto.RepresentationDTO;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Representation;
import com.gestioneleves.apieleves.entity.Utilisateur;

public class RepresentationMapper {

    public static Representation fromCreate(RepresentationCreateRequest req) {
        Representation r = new Representation();
        if (req.getIdUtilisateur() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdUtilisateur());
            r.setUtilisateur(u);
        }
        if (req.getIdEleve() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getIdEleve());
            r.setEleve(e);
        }
        return r;
    }

    public static Representation applyUpdate(Representation r, RepresentationDTO req) {
        if (req.getIdUtilisateur() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdUtilisateur());
            r.setUtilisateur(u);
        }
        if (req.getIdEleve() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getIdEleve());
            r.setEleve(e);
        }
        return r;
    }

    public static RepresentationDTO toDto(Representation r) {
        RepresentationDTO dto = new RepresentationDTO();
        dto.setIdRepresentation(r.getIdRepresentation());
        if (r.getUtilisateur() != null) {
            dto.setIdUtilisateur(r.getUtilisateur().getIdUtilisateur());
            // Test pour affichage plus pratique dans Json
            dto.setNomCompletEleve(r.getEleve().getNom() + " " + r.getEleve().getPrenom());
        }
        if (r.getEleve() != null) {
            dto.setIdEleve(r.getEleve().getIdEleve());
            // Test pour affichage plus pratique dans Json
            dto.setNomCompletUtilisateur(r.getUtilisateur().getNom() + " " + r.getUtilisateur().getPrenom());
        }
        return dto;
    }
}

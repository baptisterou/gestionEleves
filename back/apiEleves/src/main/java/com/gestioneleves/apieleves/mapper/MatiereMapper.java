package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.MatiereCreateRequest;
import com.gestioneleves.apieleves.dto.MatiereDTO;
import com.gestioneleves.apieleves.dto.MatiereUpdateRequest;
import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Utilisateur;

public class MatiereMapper {

    public static Matiere fromCreate(MatiereCreateRequest req) {
        Matiere m = new Matiere();
        m.setIntituleMatiere(req.getIntituleMatiere());
        if (req.getIdEnseignant() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdEnseignant());
            m.setEnseignant(u);
        }
        return m;
    }

    public static Matiere applyUpdate(Matiere target, MatiereUpdateRequest req) {
        if (req.getIntituleMatiere() != null) target.setIntituleMatiere(req.getIntituleMatiere());
        if (req.getIdEnseignant() != null) {
            Utilisateur u = new Utilisateur();
            u.setIdUtilisateur(req.getIdEnseignant());
            target.setEnseignant(u);
        }
        return target;
    }

    public static MatiereDTO toDto(Matiere m) {
        MatiereDTO dto = new MatiereDTO();
        dto.setIdMatiere(m.getIdMatiere());
        dto.setIntituleMatiere(m.getIntituleMatiere());
        if (m.getEnseignant() != null) dto.setIdEnseignant(m.getEnseignant().getIdUtilisateur());
        return dto;
    }
}

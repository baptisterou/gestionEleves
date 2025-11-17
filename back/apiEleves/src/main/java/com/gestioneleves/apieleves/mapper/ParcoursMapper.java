package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.ParcoursCreateRequest;
import com.gestioneleves.apieleves.dto.ParcoursDTO;
import com.gestioneleves.apieleves.entity.Classe;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Parcours;

public class ParcoursMapper {

    public static Parcours fromCreate(ParcoursCreateRequest req) {
        Parcours p = new Parcours();
        if (req.getIdClasse() != null) {
            Classe c = new Classe();
            c.setIdClasse(req.getIdClasse());
            p.setClasse(c);
        }
        if (req.getIdEleve() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getIdEleve());
            p.setEleve(e);
        }
        return p;
    }

    public static Parcours applyUpdate(Parcours p, ParcoursDTO req) {
        if (req.getIdClasse() != null) {
            Classe c = new Classe();
            c.setIdClasse(req.getIdClasse());
            p.setClasse(c);
        }
        if (req.getIdEleve() != null) {
            Eleve e = new Eleve();
            e.setIdEleve(req.getIdEleve());
            p.setEleve(e);
        }
        return p;
    }

    public static ParcoursDTO toDto(Parcours p) {
        ParcoursDTO dto = new ParcoursDTO();
        dto.setIdParcours(p.getIdParcours());
        if (p.getClasse() != null) {
            dto.setIdClasse(p.getClasse().getIdClasse());
        }
        if (p.getEleve() != null) {
            dto.setIdEleve(p.getEleve().getIdEleve());
        }
        return dto;
    }
}

package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.InscriptionCreateRequest;
import com.gestioneleves.apieleves.dto.InscriptionDTO;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Inscription;
import com.gestioneleves.apieleves.entity.Utilisateur;

public class InscriptionMapper {

    public static Inscription fromCreate(InscriptionCreateRequest req) {
        // Note: Cette méthode est conservée pour la compatibilité mais ne devrait plus être utilisée
        // La création d'inscription est maintenant entièrement gérée dans le service
        // pour garantir l'utilisation d'entités complètes

        Inscription i = new Inscription();
        i.setDateInscrip(req.getDateInscrip());

        // Création d'entités partielles uniquement pour les IDs
        // Le service se chargera de récupérer les entités complètes
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

        // Note: La mise à jour des entités liées (Eleve et Utilisateur) 
        // doit être effectuée dans le service pour récupérer les entités complètes
        // Cette méthode ne met à jour que les IDs pour que le service puisse faire les recherches

        if (req.getIdUtilisateur() != null) {
            if (i.getUtilisateur() == null) {
                i.setUtilisateur(new Utilisateur());
            }
            i.getUtilisateur().setIdUtilisateur(req.getIdUtilisateur());
        }

        if (req.getIdEleve() != null) {
            if (i.getEleve() == null) {
                i.setEleve(new Eleve());
            }
            i.getEleve().setIdEleve(req.getIdEleve());
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

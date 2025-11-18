package com.gestioneleves.apieleves.mapper;

import com.gestioneleves.apieleves.dto.MatiereCreateRequest;
import com.gestioneleves.apieleves.dto.MatiereDTO;
import com.gestioneleves.apieleves.dto.MatiereUpdateRequest;
import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Utilisateur;

/**
 * Utilitaires de mapping entre les modèles métiers {@link Matiere} et leurs représentations DTO.
 *
 * Principes:
 * - Les méthodes "from" créent une entité à partir d'une requête (DTO d'entrée)
 * - Les méthodes "apply" appliquent une mise à jour partielle sur une entité existante (champs non nuls)
 * - Les méthodes "toDto" convertissent une entité en DTO destiné à l'exposition API
 *
 * Note: Ce mapper est volontairement simple et ne gère pas les relations complexes.
 */
public class MatiereMapper {

    /**
     * Construit une entité {@link Matiere} à partir d'une requête de création.
     *
     * @param req payload de création (non null)
     * @return entité initialisée
     */
    public static Matiere fromCreate(MatiereCreateRequest req) {
        Matiere m = new Matiere();
        m.setIntituleMatiere(req.getIntituleMatiere());
        return m;
    }

    /**
     * Applique les champs non nuls de la requête de mise à jour sur l'entité cible.
     *
     * @param target entité à modifier (non null)
     * @param req payload de mise à jour (peut contenir des champs nulls)
     * @return l'entité modifiée (même instance)
     */
    public static Matiere applyUpdate(Matiere target, MatiereUpdateRequest req) {
        if (req.getIntituleMatiere() != null) target.setIntituleMatiere(req.getIntituleMatiere());
        return target;
    }

    /**
     * Convertit une entité {@link Matiere} en {@link MatiereDTO} pour exposition via l'API.
     *
     * @param m entité source (non null)
     * @return DTO correspondant
     */
    public static MatiereDTO toDto(Matiere m) {
        MatiereDTO dto = new MatiereDTO();
        dto.setIdMatiere(m.getIdMatiere());
        dto.setIntituleMatiere(m.getIntituleMatiere());
        return dto;
    }
}

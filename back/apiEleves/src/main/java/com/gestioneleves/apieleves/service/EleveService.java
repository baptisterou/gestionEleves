package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.EleveCreateRequest;
import com.gestioneleves.apieleves.dto.EleveDTO;
import com.gestioneleves.apieleves.dto.EleveUpdateRequest;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.EleveMapper;
import com.gestioneleves.apieleves.repository.EleveRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour la gestion des {@code Eleve}.
 *
 * Responsabilités:
 * - Créer, lister, mettre à jour et supprimer des élèves
 * - Valider et appliquer des mises à jour partielles
 * - Orchestrer la pagination et le mapping DTO lorsque nécessaire
 *
 * Transactions:
 * - Toutes les méthodes s'exécutent dans un contexte transactionnel (classe annotée {@link jakarta.transaction.Transactional}).
 *
 * Exceptions:
 * - {@link jakarta.persistence.EntityNotFoundException} si l'élève n'existe pas
 * - {@link IllegalArgumentException} pour les erreurs de validation d'entrée
 */
@Service
@Transactional
public class EleveService {

    private final EleveRepository eleveRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public EleveService(EleveRepository eleveRepository) {
        this.eleveRepository = eleveRepository;
    }

    /**
     * Crée un élève à partir d'une requête de création et retourne un DTO.
     *
     * @param request données de création (champs requis)
     * @return l'élève créé sous forme de {@link EleveDTO}
     */
    public EleveDTO createEleve(EleveCreateRequest request) {
        Eleve toSave = EleveMapper.fromCreate(request);
        Eleve saved = createEleve(toSave);
        return EleveMapper.toDto(saved);
    }

    /**
     * Persiste une nouvelle entité élève.
     *
     * @param eleve entité à sauvegarder
     * @return entité sauvegardée
     */
    public Eleve createEleve(Eleve eleve) {
        return eleveRepository.save(eleve);
    }

    /**
     * Récupère tous les élèves (non paginé).
     *
     * @return liste d'élèves
     */
    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    /**
     * Récupère les élèves de manière paginée.
     *
     * @param pageable paramètres de pagination/tri
     * @return page d'élèves
     */
    public Page<Eleve> getAllEleves(Pageable pageable) {
        return eleveRepository.findAll(pageable);
    }

    /**
     * Met à jour partiellement un élève et retourne un DTO.
     *
     * @param id identifiant de l'élève à modifier
     * @param request champs à mettre à jour (partiel)
     * @return l'élève mis à jour sous forme de {@link EleveDTO}
     */
    public EleveDTO editEleve(Long id, EleveUpdateRequest request) {
        Eleve current = getEleveById(id);
        Eleve updatedEntity = EleveMapper.applyUpdate(current, request);
        Eleve saved = editEleve(id, updatedEntity);
        return EleveMapper.toDto(saved);
    }

    /**
     * Applique une mise à jour partielle à une entité élève et la persiste.
     *
     * @param id identifiant
     * @param eleve valeurs à appliquer (champs non nuls)
     * @return entité sauvegardée
     * @throws EntityNotFoundException si l'élève n'existe pas
     */
    public Eleve editEleve(Long id, Eleve eleve){
        // Récupération ou exception si non trouvé
        Eleve existing = eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Elève introuvable : " + id));

        // Mise à jour des champs simples
        if (eleve.getNom() != null) {
            existing.setNom(eleve.getNom());
        }
        if (eleve.getPrenom() != null) {
            existing.setPrenom(eleve.getPrenom());
        }
        if (eleve.getDateNaissance() != null) {
            existing.setDateNaissance(eleve.getDateNaissance());
        }

        // Sauvegarde et retour
        return eleveRepository.save(existing);
    }

    /**
     * Récupère un élève par identifiant.
     *
     * @param id identifiant recherché
     * @return entité trouvée
     * @throws EntityNotFoundException si aucune correspondance
     */
    public Eleve getEleveById(Long id){
        return eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Eleve introuvable: " + id));
    }

    /**
     * Supprime un élève.
     *
     * @param idEleve identifiant de l'élève à supprimer
     * @throws EntityNotFoundException si l'élève n'existe pas
     */
    public void deleteEleve(Long idEleve) {
        if (!eleveRepository.existsById(idEleve)) {
            throw new EntityNotFoundException("Eleve introuvable: " + idEleve);
        }
        eleveRepository.deleteById(idEleve);
    }
}

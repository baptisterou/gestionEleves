package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.MatiereCreateRequest;
import com.gestioneleves.apieleves.dto.MatiereDTO;
import com.gestioneleves.apieleves.dto.MatiereUpdateRequest;
import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.MatiereMapper;
import com.gestioneleves.apieleves.repository.MatiereRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour la gestion des {@code Matiere}.
 *
 * Responsabilités:
 * - Créer, lire, mettre à jour et supprimer des matières
 * - Appliquer la logique de validation/mise à jour partielle
 * - Orchestrer la pagination et le mapping vers des DTO lorsque nécessaire
 *
 * Transactions:
 * - Toutes les méthodes sont exécutées dans un contexte transactionnel (classe annotée {@link jakarta.transaction.Transactional}).
 *
 * Exceptions:
 * - {@link jakarta.persistence.EntityNotFoundException} est levée si une ressource n'est pas trouvée
 */
@Service
@Transactional
public class MatiereService {

    private final MatiereRepository matiereRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public MatiereService(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    /**
     * Crée une matière à partir d'une requête de création et retourne un DTO.
     *
     * @param request données de création (champs requis)
     * @return la matière créée sous forme de {@link MatiereDTO}
     */
    public MatiereDTO createMatiere(MatiereCreateRequest request) {
        Matiere toSave = MatiereMapper.fromCreate(request);
        Matiere saved = createMatiere(toSave);
        return MatiereMapper.toDto(saved);
    }

    /**
     * Persiste une nouvelle entité matière.
     *
     * @param matiere entité à persister
     * @return entité sauvegardée
     */
    public Matiere createMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    /**
     * Récupère toutes les matières sans pagination.
     *
     * @return liste des entités matières
     */
    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    /**
     * Récupère les matières de manière paginée.
     *
     * @param pageable paramètres de pagination/tri
     * @return page d'entités matières
     */
    public Page<Matiere> getAllMatieres(Pageable pageable) {
        return matiereRepository.findAll(pageable);
    }

    /**
     * Met à jour partiellement une matière et retourne un DTO.
     *
     * @param id identifiant de la matière à modifier
     * @param request données de mise à jour (champs optionnels)
     * @return la matière mise à jour sous forme de {@link MatiereDTO}
     */
    public MatiereDTO editMatiere(Long id, MatiereUpdateRequest request) {
        Matiere current = getMatiereById(id);
        Matiere updated = MatiereMapper.applyUpdate(current, request);
        Matiere saved = editMatiere(id, updated);
        return MatiereMapper.toDto(saved);
    }

    /**
     * Applique une mise à jour partielle à une entité matière existante et la persiste.
     *
     * @param id identifiant de la matière
     * @param matiere valeurs à appliquer (champs non nuls uniquement)
     * @return entité sauvegardée
     * @throws EntityNotFoundException si la matière n'existe pas
     */
    public Matiere editMatiere(Long id, Matiere matiere) {
        // Récupération ou exception si non trouvé
        Matiere existing = matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière introuvable : " + id));

        // Mise à jour des champs simples
        if (matiere.getIntituleMatiere() != null) {
            existing.setIntituleMatiere(matiere.getIntituleMatiere());
        }

        // Sauvegarde et retour
        return matiereRepository.save(existing);
    }

    /**
     * Récupère une matière par identifiant.
     *
     * @param id identifiant recherché
     * @return entité trouvée
     * @throws EntityNotFoundException si aucune matière ne correspond
     */
    public Matiere getMatiereById(Long id){
        return matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matiere introuvable: " + id));
    }

    /**
     * Supprime une matière par identifiant.
     *
     * @param id_matiere identifiant de la matière à supprimer
     * @throws EntityNotFoundException si la matière n'existe pas
     */
    public void deleteMatiere(Long id_matiere) {
        if (!matiereRepository.existsById(id_matiere)) {
            throw new EntityNotFoundException("Matiere introuvable: " + id_matiere);
        }
        matiereRepository.deleteById(id_matiere);
    }
}

package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.BulletinCreateRequest;
import com.gestioneleves.apieleves.dto.BulletinDTO;
import com.gestioneleves.apieleves.dto.BulletinUpdateRequest;
import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.mapper.BulletinMapper;
import com.gestioneleves.apieleves.repository.BulletinRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

/**
 * Service métier pour la gestion des {@code Bulletin}.
 *
 * Responsabilités:
 * - Créer, lister, mettre à jour et supprimer des bulletins
 * - Appliquer des validations métier (trimestre, année)
 * - Orchestrer la pagination et le mapping DTO
 *
 * Transactions:
 * - Toutes les méthodes s'exécutent dans un contexte transactionnel (classe annotée {@link jakarta.transaction.Transactional}).
 *
 * Exceptions:
 * - {@link jakarta.persistence.EntityNotFoundException} si le bulletin n'existe pas
 * - {@link IllegalArgumentException} en cas de données invalides (trimestre/année)
 */
@Service
@Transactional
public class BulletinService {

    private final BulletinRepository bulletinRepository;

    public BulletinService(BulletinRepository bulletinRepository) {
        this.bulletinRepository = bulletinRepository;
    }

    /**
     * Crée un bulletin à partir d'une requête de création et retourne un DTO.
     *
     * @param request données de création
     * @return bulletin créé sous forme de {@link BulletinDTO}
     */
    public BulletinDTO createBulletin(BulletinCreateRequest request) {
        Bulletin toSave = BulletinMapper.fromCreate(request);
        Bulletin saved = createBulletin(toSave);
        return BulletinMapper.toDto(saved);
    }

    /**
     * Persiste un nouveau bulletin après validation métier (trimestre/année).
     *
     * @param bulletin entité à sauvegarder
     * @return entité sauvegardée
     */
    public Bulletin createBulletin(Bulletin bulletin) {
        validateBulletinData(bulletin);
        return bulletinRepository.save(bulletin);
    }

    /**
     * Récupère tous les bulletins (non paginé).
     */
    public List<Bulletin> getAllBulletins() {
         return bulletinRepository.findAll();
    }

    /**
     * Récupère les bulletins paginés.
     */
    public Page<Bulletin> getAllBulletins(Pageable pageable) {
        return bulletinRepository.findAll(pageable);
    }

    /**
     * Met à jour partiellement un bulletin et retourne un DTO.
     */
    public BulletinDTO editBulletin(Long id, BulletinUpdateRequest request) {
        Bulletin current = getBulletinById(id);
        Bulletin updated = BulletinMapper.applyUpdate(current, request);
        Bulletin saved = editBulletin(id, updated);
        return BulletinMapper.toDto(saved);
    }

    /**
     * Applique une mise à jour partielle sur un bulletin existant.
     *
     * @throws EntityNotFoundException si le bulletin n'existe pas
     */
    public Bulletin editBulletin(Long id, Bulletin bulletin){
        Bulletin entite = bulletinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bulletin introuvable: " + id));

        if (bulletin.getTrimestreBulletin() != 0) {
            validateTrimestre(bulletin.getTrimestreBulletin());
            entite.setTrimestreBulletin(bulletin.getTrimestreBulletin());
        }
        if (bulletin.getAnneeBulletin() != 0) {
            validateAnnee(bulletin.getAnneeBulletin());
            entite.setAnneeBulletin(bulletin.getAnneeBulletin());
        }
        if (bulletin.getCommentaire() != null) {
            entite.setCommentaire(bulletin.getCommentaire());
        }
        return bulletinRepository.save(entite);
    }

    /**
     * Récupère un bulletin par identifiant.
     */
    public Bulletin getBulletinById(Long id){
        return bulletinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bulletin introuvable: " + id));
    }

    /**
     * Supprime un bulletin par identifiant.
     *
     * @throws EntityNotFoundException si le bulletin n'existe pas
     */
    public void deleteBulletin(Long idBulletin) {
        if (!bulletinRepository.existsById(idBulletin)) {
            throw new EntityNotFoundException("Bulletin introuvable: " + idBulletin);
        }
        bulletinRepository.deleteById(idBulletin);
    }

    /**
     * Valide les données du bulletin (trimestre/année).
     */
    private void validateBulletinData(Bulletin b) {
        validateTrimestre(b.getTrimestreBulletin());
        validateAnnee(b.getAnneeBulletin());
    }

    /**
     * Vérifie que le trimestre est dans {1,2,3}.
     */
    private void validateTrimestre(int trimestre) {
        if (trimestre < 1 || trimestre > 3) {
            throw new IllegalArgumentException("Le trimestre doit être dans {1,2,3}");
        }
    }

    /**
     * Vérifie que l'année est l'année courante ou suivante.
     */
    private void validateAnnee(int annee) {
        int currentYear = Year.now().getValue();
        if (annee != currentYear && annee != currentYear + 1) {
            throw new IllegalArgumentException("L'année du bulletin doit être l'année courante ou l'année suivante");
        }
    }
}

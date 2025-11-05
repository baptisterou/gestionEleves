package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.repository.BulletinRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

/**
 * Service pour la gestion des bulletins
 * Contient la logique métier relative aux opérations sur les bulletins
 */
@Service // Indique que cette classe est un service Spring (gérée comme un bean)
public class BulletinService {

    private final BulletinRepository bulletinRepository;

    public BulletinService(BulletinRepository bulletinRepository) {
        this.bulletinRepository = bulletinRepository;
    }

    /**
     * Création d'un bulletin avec validation métier
     */
    public Bulletin createBulletin(Bulletin bulletin) {
        validateBulletinData(bulletin);
        return bulletinRepository.save(bulletin);
    }

    public List<Bulletin> getAllBulletins() {
         return bulletinRepository.findAll();
    }

    public Page<Bulletin> getAllBulletins(Pageable pageable) {
        return bulletinRepository.findAll(pageable);
    }

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

    public Bulletin getBulletinById(Long id){
        return bulletinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bulletin introuvable: " + id));
    }

    public void deleteBulletin(Long idBulletin) {
        if (!bulletinRepository.existsById(idBulletin)) {
            throw new EntityNotFoundException("Bulletin introuvable: " + idBulletin);
        }
        bulletinRepository.deleteById(idBulletin);
    }

    private void validateBulletinData(Bulletin b) {
        validateTrimestre(b.getTrimestreBulletin());
        validateAnnee(b.getAnneeBulletin());
    }

    private void validateTrimestre(int trimestre) {
        if (trimestre < 1 || trimestre > 3) {
            throw new IllegalArgumentException("Le trimestre doit être dans {1,2,3}");
        }
    }

    private void validateAnnee(int annee) {
        int currentYear = Year.now().getValue();
        if (annee != currentYear && annee != currentYear + 1) {
            throw new IllegalArgumentException("L'année du bulletin doit être l'année courante ou l'année suivante");
        }
    }
}

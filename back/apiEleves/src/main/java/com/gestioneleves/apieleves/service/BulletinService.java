package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.BulletinRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des bulletins
 * Contient la logique métier relative aux opérations sur les bulletins
 */
@Service // Indique que cette classe est un service Spring (gérée comme un bean)
public class BulletinService {

    // Injection du repository pour accéder aux données des bulletins
    // Spring fournit automatiquement une instance de BulletinRepository
    @Autowired
    private BulletinRepository bulletinRepository;

    /**
     * Récupère la liste de tous les bulletins
     * @return Liste des objets Bulletin contenant tous les bulletins en base de données
     */
    public Bulletin createBulletin(Bulletin bulletin) {
        return bulletinRepository.save(bulletin);
    }

    public List<Bulletin> getAllBulletins() {
        return (List<Bulletin>) bulletinRepository.findAll();
    }

    public Bulletin editBulletin(Long id, Bulletin bulletin){
        Optional<Bulletin> entite = bulletinRepository.findById(id);
        if (!entite.isPresent()) {
            throw new EntityNotFoundException("Bulletin introuvable: " + id);
        }
        if (bulletin.getTrimestreBulletin()==1||bulletin.getTrimestreBulletin()==2||bulletin.getTrimestreBulletin()==3) {
            entite.get().setTrimestreBulletin(bulletin.getTrimestreBulletin());
        }
        if (bulletin.getAnneeBulletin() > new java.util.Date().getYear() -1 ) {
            entite.get().setAnneeBulletin(bulletin.getAnneeBulletin());
        }
        if (bulletin.getCommentaire() != null) {
            entite.get().setCommentaire(bulletin.getCommentaire());
        }

        return bulletinRepository.save(entite.get());
    }

    public Bulletin getBulletinById(Long id){
        return bulletinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable: " + id));
    }

    public void deleteBulletin(Long id_bulletin) {
        bulletinRepository.deleteById(id_bulletin);
    }
}

package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Bulletin;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.repository.BulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        // Appel au repository pour récupérer tous les bulletins
        // Le cast en List<Bulletin> est nécessaire car findAll() retourne un Iterable
        return (List<Bulletin>) bulletinRepository.findAll();
    }

    public Bulletin editBulletin(Bulletin bulletin) {
        return bulletinRepository.save(bulletin);
    }

    public void deleteBulletin(Long id_bulletin) {
        bulletinRepository.deleteById(id_bulletin);
    }
}

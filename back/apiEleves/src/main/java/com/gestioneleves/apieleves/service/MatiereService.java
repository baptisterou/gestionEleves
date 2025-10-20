package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des matières
 * Contient la logique métier relative aux opérations sur les matières
 */
@Service // Indique que cette classe est un service Spring (gérée comme un bean)
public class MatiereService {

    // Injection du repository pour accéder aux données des matières
    // Spring fournit automatiquement une instance de MatiereRepository
    @Autowired
    private MatiereRepository matiereRepository;

    /**
     * Récupère la liste de toutes les matières
     * @return Liste des objets Matiere contenant toutes les matières en base de données
     */
    public List<Matiere> getAllMatieres() {
        // Appel au repository pour récupérer toutes les matières
        // Le cast en List<Matiere> est nécessaire car findAll() retourne un Iterable
        return (List<Matiere>) matiereRepository.findAll();
    }
}
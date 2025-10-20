package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.repository.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des élèves
 * Contient la logique métier relative aux opérations sur les élèves
 */
@Service // Indique que cette classe est un service Spring (gérée comme un bean)
public class EleveService {

    // Injection du repository pour accéder aux données des élèves
    // Spring fournit automatiquement une instance de EleveRepository
    @Autowired
    private EleveRepository eleveRepository;

    /**
     * Récupère la liste de tous les élèves
     * @return Liste des objets Eleve contenant tous les élèves en base de données
     */
    public List<Eleve> getAllEleves() {
        // Appel au repository pour récupérer tous les élèves
        // Le cast en List<Eleve> est nécessaire car findAll() retourne un Iterable
        return (List<Eleve>) eleveRepository.findAll();
    }
}
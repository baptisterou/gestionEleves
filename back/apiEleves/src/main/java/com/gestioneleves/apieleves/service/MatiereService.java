package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gestioneleves.apieleves.entity.Eleve;

import java.util.Date;
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

    public Matiere createMatiere(String intitule_matiere) {
        return matiereRepository.save(
                new Matiere(intitule_matiere)
        );
    }

    public List<Matiere> getAllMatieres() {
        // Appel au repository pour récupérer toutes les matières
        // Le cast en List<Matiere> est nécessaire car findAll() retourne un Iterable
        return (List<Matiere>) matiereRepository.findAll();
    }

    public Matiere editMatiere(Long id_matiere, String intitule_matiere) {
        Matiere matiere = matiereRepository.findById(id_matiere).orElseThrow();
        matiere.setIntitule_matiere(intitule_matiere);

        return matiereRepository.save(matiere);
    }

    public void deleteMatiere(Long id_matiere) {
        matiereRepository.deleteById(id_matiere);
    }
}
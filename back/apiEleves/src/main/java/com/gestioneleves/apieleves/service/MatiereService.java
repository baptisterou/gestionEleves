package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.MatiereRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Matiere createMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    public List<Matiere> getAllMatieres() {
        // Appel au repository pour récupérer toutes les matières
        // Le cast en List<Matiere> est nécessaire car findAll() retourne un Iterable
        return (List<Matiere>) matiereRepository.findAll();
    }


    public Matiere editMatiere(Long id, Matiere matiere){
        Optional<Matiere> entite = matiereRepository.findById(id);
        if (!entite.isPresent()) {
            throw new EntityNotFoundException("Utilisateur introuvable: " + id);
        }
        if (matiere.getIntituleMatiere() != null) {
            entite.get().setIntituleMatiere(matiere.getIntituleMatiere());
        }
        return matiereRepository.save(entite.get());
    }

    public Matiere getMatiereById(Long id){
        return matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matiere introuvable: " + id));
    }

    public void deleteMatiere(Long id_matiere) {

        matiereRepository.deleteById(id_matiere);
    }
}

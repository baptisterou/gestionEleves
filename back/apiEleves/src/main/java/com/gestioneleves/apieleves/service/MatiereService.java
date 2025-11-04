package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.MatiereRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des matières
 * Contient la logique métier relative aux opérations sur les matières
 */
@Service // Indique que cette classe est un service Spring (gérée comme un bean)
public class MatiereService {

    private final MatiereRepository matiereRepository;

    public MatiereService(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    public Matiere createMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    public Page<Matiere> getAllMatieres(Pageable pageable) {
        return matiereRepository.findAll(pageable);
    }

    public Matiere editMatiere(Long id, Matiere matiere){
        Optional<Matiere> entite = matiereRepository.findById(id);
        if (!entite.isPresent()) {
            throw new EntityNotFoundException("Matiere introuvable: " + id);
        }
        if (matiere.getIntituleMatiere() != null) {
            entite.get().setIntituleMatiere(matiere.getIntituleMatiere());
        }
        if (matiere.getEnseignant() != null) {
            entite.get().setEnseignant(matiere.getEnseignant());
        }
        return matiereRepository.save(entite.get());
    }

    public Matiere getMatiereById(Long id){
        return matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matiere introuvable: " + id));
    }

    public void deleteMatiere(Long id_matiere) {
        if (!matiereRepository.existsById(id_matiere)) {
            throw new EntityNotFoundException("Matiere introuvable: " + id_matiere);
        }
        matiereRepository.deleteById(id_matiere);
    }
}

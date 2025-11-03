package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.MatiereRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
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

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public MatiereService(MatiereRepository matiereRepository, UtilisateurRepository utilisateurRepository) {
        this.matiereRepository = matiereRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Récupère la liste de toutes les matières
     * @return Liste des objets Matiere contenant toutes les matières en base de données
     */

    public Matiere createMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    public Page<Matiere> getAllMatieres(Pageable pageable) {
        return matiereRepository.findAll(pageable);
    }

    public Matiere editMatiere(Long id, Matiere matiere) {
        Matiere existing = matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière introuvable : " + id));

        if (matiere.getIntituleMatiere() != null) {
            existing.setIntituleMatiere(matiere.getIntituleMatiere());
        }

        if (matiere.getEnseignant() != null && matiere.getEnseignant().getIdUtilisateur() != null) {
            Utilisateur enseignant = utilisateurRepository.findById(matiere.getEnseignant().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable : " + matiere.getEnseignant().getIdUtilisateur()));
            existing.setEnseignant(enseignant);
        }

        return matiereRepository.save(existing);
    }
        if (matiere.getIntituleMatiere() != null) {
            existing.setIntituleMatiere(matiere.getIntituleMatiere());
        }
        if (matiere.getIntituleMatiere() != null) {
            existing.setIntituleMatiere(matiere.getIntituleMatiere());
        }

        if (matiere.getEnseignant() != null && matiere.getEnseignant().getIdUtilisateur() != null) {
            Utilisateur enseignant = utilisateurRepository.findById(matiere.getEnseignant().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable : " + matiere.getEnseignant().getIdUtilisateur()));
            existing.setEnseignant(enseignant);
        }

        return matiereRepository.save(existing);
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

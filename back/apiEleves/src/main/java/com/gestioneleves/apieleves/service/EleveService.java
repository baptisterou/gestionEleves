package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.EleveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     *
     * @return Liste des objets Eleve contenant tous les élèves en base de données
     */
    public Eleve createEleve(Eleve eleve) {

        return eleveRepository.save(eleve);
    }

    public List<Eleve> getAllEleves() {
        // Appel au repository pour récupérer tous les élèves
        // Le cast en List<Eleve> est nécessaire car findAll() retourne un Iterable
        return (List<Eleve>) eleveRepository.findAll();
    }



    public Eleve editEleve(Long id, Eleve eleve){
        Optional<Eleve> entite = eleveRepository.findById(id);
        if (!entite.isPresent()) {
            throw new EntityNotFoundException("Eleve introuvable: " + id);
        }
        if (eleve.getNom() != null) {
            entite.get().setNom(eleve.getNom());
        }
        if (eleve.getPrenom() != null) {
            entite.get().setPrenom(eleve.getPrenom());
        }
        if (eleve.getDateNaissance() != null) {
            entite.get().setDateNaissance(eleve.getDateNaissance());
        }
        return eleveRepository.save(entite.get());
    }

    public Eleve getEleveById(Long id){
        return eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Eleve introuvable: " + id));
    }

    public void deleteEleve(Long id_eleve) {

        eleveRepository.deleteById(id_eleve);
    }
}

package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.repository.EleveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des élèves
 * Contient la logique métier relative aux opérations sur les élèves
 */
@Service // Indique que cette classe est un service Spring (gérée comme un bean)
public class EleveService {

    private final EleveRepository eleveRepository;

    public EleveService(EleveRepository eleveRepository) {
        this.eleveRepository = eleveRepository;
    }

    public Eleve createEleve(Eleve eleve) {
        return eleveRepository.save(eleve);
    }

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Page<Eleve> getAllEleves(Pageable pageable) {
        return eleveRepository.findAll(pageable);
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
        if (eleve.getUtilisateur() != null) {
            entite.get().setUtilisateur(eleve.getUtilisateur());
        }
        return eleveRepository.save(entite.get());
    }

    public Eleve getEleveById(Long id){
        return eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Eleve introuvable: " + id));
    }

    public void deleteEleve(Long idEleve) {
        if (!eleveRepository.existsById(idEleve)) {
            throw new EntityNotFoundException("Eleve introuvable: " + idEleve);
        }
        eleveRepository.deleteById(idEleve);
    }
}

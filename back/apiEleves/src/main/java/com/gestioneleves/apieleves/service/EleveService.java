package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.repository.EleveRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
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

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
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
        // Récupération ou exception si non trouvé
        Eleve existing = eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Elève introuvable : " + id));

        // Mise à jour des champs simples
        if (eleve.getNom() != null) {
            eleve.setNom(eleve.getNom());
        }
        if (eleve.getPrenom() != null) {
            eleve.setPrenom(eleve.getPrenom());
        }
        if (eleve.getDateNaissance() != null) {
            eleve.setDateNaissance(eleve.getDateNaissance());
        }
        if (eleve.getUtilisateur() != null && eleve.getUtilisateur().getIdUtilisateur() != null) {
            Utilisateur representant = utilisateurRepository.findById(eleve.getUtilisateur().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant introuvable : " + eleve.getUtilisateur().getIdUtilisateur()));
            entite.get().setUtilisateur(representant);
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

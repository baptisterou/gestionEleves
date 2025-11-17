package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.EleveCreateRequest;
import com.gestioneleves.apieleves.dto.EleveDTO;
import com.gestioneleves.apieleves.dto.EleveUpdateRequest;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.EleveMapper;
import com.gestioneleves.apieleves.repository.EleveRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des élèves
 * Contient la logique métier relative aux opérations sur les élèves
 */
@Service // Indique que cette classe est un service Spring (gérée comme un bean)
@Transactional
public class EleveService {

    private final EleveRepository eleveRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public EleveService(EleveRepository eleveRepository) {
        this.eleveRepository = eleveRepository;
    }

    // Variante contrôleur-friendly: le service accepte la request et renvoie le DTO
    public EleveDTO createEleve(EleveCreateRequest request) {
        Eleve toSave = EleveMapper.fromCreate(request);
        Eleve saved = createEleve(toSave);
        return EleveMapper.toDto(saved);
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

    // Variante contrôleur-friendly: update avec request en entrée et DTO en sortie
    public EleveDTO editEleve(Long id, EleveUpdateRequest request) {
        Eleve current = getEleveById(id);
        Eleve updatedEntity = EleveMapper.applyUpdate(current, request);
        Eleve saved = editEleve(id, updatedEntity);
        return EleveMapper.toDto(saved);
    }

    public Eleve editEleve(Long id, Eleve eleve){
        // Récupération ou exception si non trouvé
        Eleve existing = eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Elève introuvable : " + id));

        // Mise à jour des champs simples
        if (eleve.getNom() != null) {
            existing.setNom(eleve.getNom());
        }
        if (eleve.getPrenom() != null) {
            existing.setPrenom(eleve.getPrenom());
        }
        if (eleve.getDateNaissance() != null) {
            existing.setDateNaissance(eleve.getDateNaissance());
        }

        // Sauvegarde et retour
        return eleveRepository.save(existing);
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

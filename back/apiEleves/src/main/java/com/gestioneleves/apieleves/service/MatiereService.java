package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.MatiereCreateRequest;
import com.gestioneleves.apieleves.dto.MatiereDTO;
import com.gestioneleves.apieleves.dto.MatiereUpdateRequest;
import com.gestioneleves.apieleves.entity.Matiere;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.MatiereMapper;
import com.gestioneleves.apieleves.repository.MatiereRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des matières
 * Contient la logique métier relative aux opérations sur les matières
 */
@Service // Indique que cette classe est un service Spring (gérée comme un bean)
@Transactional
public class MatiereService {

    private final MatiereRepository matiereRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public MatiereService(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    // Variante contrôleur-friendly
    public MatiereDTO createMatiere(MatiereCreateRequest request) {
        Matiere toSave = MatiereMapper.fromCreate(request);
        Matiere saved = createMatiere(toSave);
        return MatiereMapper.toDto(saved);
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

    // Variante contrôleur-friendly
    public MatiereDTO editMatiere(Long id, MatiereUpdateRequest request) {
        Matiere current = getMatiereById(id);
        Matiere updated = MatiereMapper.applyUpdate(current, request);
        Matiere saved = editMatiere(id, updated);
        return MatiereMapper.toDto(saved);
    }

    public Matiere editMatiere(Long id, Matiere matiere) {
        // Récupération ou exception si non trouvé
        Matiere existing = matiereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matière introuvable : " + id));

        // Mise à jour des champs simples
        if (matiere.getIntituleMatiere() != null) {
            existing.setIntituleMatiere(matiere.getIntituleMatiere());
        }

        // Sauvegarde et retour
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

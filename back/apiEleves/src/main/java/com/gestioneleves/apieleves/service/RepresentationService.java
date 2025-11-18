package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.RepresentationCreateRequest;
import com.gestioneleves.apieleves.dto.RepresentationDTO;
import com.gestioneleves.apieleves.entity.Eleve;
import com.gestioneleves.apieleves.entity.Representation;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.RepresentationMapper;
import com.gestioneleves.apieleves.repository.EleveRepository;
import com.gestioneleves.apieleves.repository.RepresentationRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RepresentationService {

    private final RepresentationRepository representationRepository;

    public RepresentationService(RepresentationRepository representationRepository) {
        this.representationRepository = representationRepository;
    }

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Representation> getAllRepresentations(){ return representationRepository.findAll(); }

    // Variante contrôleur-friendly
    public RepresentationDTO createRepresentation(RepresentationCreateRequest request){
        Representation toSave = RepresentationMapper.fromCreate(request);
        Representation saved = createRepresentation(toSave);
        return RepresentationMapper.toDto(saved);
    }

    public Representation createRepresentation(Representation representation){ return representationRepository.save(representation); }

    // Variante contrôleur-friendly: update avec request en entrée et DTO en sortie
    public RepresentationDTO editRepresentation(Long id, RepresentationDTO request) {
        Representation current = getRepresentationById(id);
        Representation updatedEntity = RepresentationMapper.applyUpdate(current, request);
        Representation saved = editRepresentation(id, updatedEntity);
        return RepresentationMapper.toDto(saved);
    }

    public Representation editRepresentation(Long id, Representation representation){
        // Récupération ou exception si non trouvé
        Representation existing = representationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Representation introuvable : " + id));

        // Mise à jour de l'élève lié (si fourni)
        if (representation.getEleve() != null && representation.getEleve().getIdEleve() != null) {
            Eleve eleve = eleveRepository.findById(representation.getEleve().getIdEleve())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Élève introuvable : " + representation.getEleve().getIdEleve()));
            existing.setEleve(eleve);
        }

        // Mise à jour de l'utilisateur lié (si fourni)
        if (representation.getUtilisateur() != null && representation.getUtilisateur().getIdUtilisateur() != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(representation.getUtilisateur().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Utilisateur introuvable : " + representation.getUtilisateur().getIdUtilisateur()));
            existing.setUtilisateur(utilisateur);
        }

        //Sauvegarde et retour
        return representationRepository.save(existing); }

    public void deleteRepresentation(Long id){
        if (!representationRepository.existsById(id)) {
            throw new EntityNotFoundException("Representation introuvable:" + id);
        }
        representationRepository.deleteById(id);
    }

    public Representation getRepresentationById(Long id){
        return representationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Representation introuvable: " + id));
    }
}

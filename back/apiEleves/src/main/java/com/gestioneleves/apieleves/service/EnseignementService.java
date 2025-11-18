package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.EnseignementCreateRequest;
import com.gestioneleves.apieleves.dto.EnseignementDTO;
import com.gestioneleves.apieleves.entity.*;
import com.gestioneleves.apieleves.mapper.EnseignementMapper;
import com.gestioneleves.apieleves.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service métier pour la gestion des {@code Enseignement} (lien enseignant ↔ classe ↔ matière).
 *
 * Responsabilités:
 * - Lister, créer, modifier et supprimer des enseignements
 * - Gérer les associations avec {@link Classe}, {@link Utilisateur} (enseignant) et {@link Matiere}
 * - Exposer des variantes orientées contrôleur (entrée requête/DTO, sortie DTO)
 *
 * Transactions:
 * - Toutes les méthodes s'exécutent dans un contexte transactionnel (classe annotée {@link org.springframework.transaction.annotation.Transactional}).
 *
 * Exceptions:
 * - {@link jakarta.persistence.EntityNotFoundException} si l'enseignement/ressource liée est introuvable
 */
@Service
@Transactional
public class EnseignementService {

    private final EnseignementRepository enseignementRepository;

    public EnseignementService(EnseignementRepository enseignementRepository) {
        this.enseignementRepository = enseignementRepository;
    }

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MatiereRepository matiereRepository;


    /**
     * Récupère tous les enseignements (non paginé).
     */
    public List<Enseignement> getAllEnseignements(){ return enseignementRepository.findAll(); }

    /**
     * Crée un enseignement à partir d'une requête de création et retourne un DTO.
     */
    public EnseignementDTO createEnseignement(EnseignementCreateRequest request){
        Enseignement toSave = EnseignementMapper.fromCreate(request);
        Enseignement saved = createEnseignement(toSave);
        return EnseignementMapper.toDto(saved);
    }

    /**
     * Persiste une entité enseignement.
     */
    public Enseignement createEnseignement(Enseignement enseignement){ return enseignementRepository.save(enseignement); }

    /**
     * Met à jour partiellement un enseignement et retourne un DTO.
     */
    public EnseignementDTO editEnseignement(Long id, EnseignementDTO request) {
        Enseignement current = getEnseignementById(id);
        Enseignement updatedEntity = EnseignementMapper.applyUpdate(current, request);
        Enseignement saved = editEnseignement(id, updatedEntity);
        return EnseignementMapper.toDto(saved);
    }

    /**
     * Applique une mise à jour partielle à un enseignement existant.
     *
     * @throws EntityNotFoundException si l'enseignement ou une ressource liée n'existe pas
     */
    public Enseignement editEnseignement(Long id, Enseignement enseignement){
        // Récupération ou exception si non trouvé
        Enseignement existing = enseignementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignement introuvable : " + id));

        // Mise à jour de la classe lié (si fourni)
        if (enseignement.getClasse() != null && enseignement.getClasse().getIdClasse() != null) {
            Classe classe = classeRepository.findById(enseignement.getClasse().getIdClasse())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Élève introuvable : " + enseignement.getClasse().getIdClasse()));
            existing.setClasse(classe);
        }

        // Mise à jour de l'utilisateur lié (si fourni)
        if (enseignement.getEnseignant() != null && enseignement.getEnseignant().getIdUtilisateur() != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(enseignement.getEnseignant().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Utilisateur introuvable : " + enseignement.getEnseignant().getIdUtilisateur()));
            existing.setEnseignant(utilisateur);
        }

        //Mise à jour de la matière liée (si fourni)
        if (enseignement.getMatiere() != null && enseignement.getMatiere().getIdMatiere() != null) {
            Matiere matiere = matiereRepository.findById(enseignement.getMatiere().getIdMatiere())
                    .orElseThrow(() -> new EntityNotFoundException( "Matière introuvable : " + enseignement.getMatiere().getIdMatiere()));
            existing.setMatiere(matiere);
        }

        //Sauvegarde et retour
        return enseignementRepository.save(existing); }

    /**
     * Supprime un enseignement par identifiant.
     */
    public void deleteEnseignement(Long id){
        if (!enseignementRepository.existsById(id)) {
            throw new EntityNotFoundException("Enseignement introuvable:" + id);
        }
        enseignementRepository.deleteById(id);
    }

    /**
     * Récupère un enseignement par identifiant.
     */
    public Enseignement getEnseignementById(Long id){
        return enseignementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignement introuvable: " + id));
    }
}

package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.dto.InscriptionCreateRequest;
import com.gestioneleves.apieleves.dto.InscriptionDTO;
import com.gestioneleves.apieleves.entity.*;
import com.gestioneleves.apieleves.mapper.InscriptionMapper;
import com.gestioneleves.apieleves.repository.EleveRepository;
import com.gestioneleves.apieleves.repository.InscriptionRepository;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service métier pour la gestion des {@code Inscription}.
 *
 * Responsabilités:
 * - Lister, créer, modifier et supprimer des inscriptions d'élèves
 * - Assurer l'intégrité des liens avec les entités associées ({@link Eleve} et {@link Utilisateur})
 * - Exposer des variantes orientées contrôleur (entrée requête/DTO, sortie DTO)
 *
 * Transactions:
 * - Toutes les méthodes s'exécutent dans un contexte transactionnel (classe annotée {@link org.springframework.transaction.annotation.Transactional}).
 *
 * Exceptions:
 * - {@link jakarta.persistence.EntityNotFoundException} si l'inscription/élève/utilisateur est introuvable
 */
@Service
@Transactional
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public InscriptionService(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    /**
     * Récupère toutes les inscriptions.
     *
     * @return liste d'inscriptions
     */
    public List<Inscription> getAllInscriptions(){ return inscriptionRepository.findAll(); }

    /**
     * Crée une inscription à partir d'une requête de création.
     *
     * @param request données nécessaires à la création
     * @return inscription créée sous forme de DTO
     */
    public InscriptionDTO createInscription(InscriptionCreateRequest request){
        Inscription toSave = InscriptionMapper.fromCreate(request);
        Inscription saved = createInscription(toSave);
        return InscriptionMapper.toDto(saved);
    }

    /**
     * Persiste une nouvelle inscription.
     *
     * @param inscription entité à sauvegarder
     * @return entité sauvegardée
     */
    public Inscription createInscription(Inscription inscription){ return inscriptionRepository.save(inscription); }

    /**
     * Met à jour partiellement une inscription et retourne un DTO.
     *
     * @param id identifiant de l'inscription à modifier
     * @param request champs à mettre à jour
     * @return inscription mise à jour (DTO)
     */
    public InscriptionDTO editInscription(Long id, InscriptionDTO request) {
        Inscription current = getInscriptionById(id);
        Inscription updatedEntity = InscriptionMapper.applyUpdate(current, request);
        Inscription saved = editInscription(id, updatedEntity);
        return InscriptionMapper.toDto(saved);
    }

    /**
     * Met à jour une inscription existante en appliquant uniquement les champs non nuls.
     *
     * @param id identifiant
     * @param inscription valeurs à appliquer
     * @return entité sauvegardée
     * @throws EntityNotFoundException si l'inscription, l'élève ou l'utilisateur n'existent pas
     */
    public Inscription editInscription(Long id, Inscription inscription){
        // Récupération ou exception si non trouvé
        Inscription existing = inscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable : " + id));

        // Mise à jour des champs simples
        if (inscription.getDateInscrip() != null) {
            existing.setDateInscrip(inscription.getDateInscrip());
        }

        // Mise à jour de l'élève lié (si fourni)
        if (inscription.getEleve() != null && inscription.getEleve().getIdEleve() != null) {
            Eleve eleve = eleveRepository.findById(inscription.getEleve().getIdEleve())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Élève introuvable : " + inscription.getEleve().getIdEleve()));
            existing.setEleve(eleve);
        }

        // Mise à jour de l'utilisateur lié (si fourni)
        if (inscription.getUtilisateur() != null && inscription.getUtilisateur().getIdUtilisateur() != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(inscription.getUtilisateur().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Utilisateur introuvable : " + inscription.getUtilisateur().getIdUtilisateur()));
            existing.setUtilisateur(utilisateur);
        }

        // Sauvegarde et retour
        return inscriptionRepository.save(existing); }

    /**
     * Supprime une inscription par identifiant.
     *
     * @param id identifiant de l'inscription
     * @throws EntityNotFoundException si l'inscription n'existe pas
     */
    public void deleteInscription(Long id){
        if (!inscriptionRepository.existsById(id)) {
            throw new EntityNotFoundException("Inscription introuvable:" + id);
        }
        inscriptionRepository.deleteById(id);
    }

    /**
     * Récupère une inscription par identifiant.
     *
     * @param id identifiant recherché
     * @return entité trouvée
     * @throws EntityNotFoundException si aucune inscription ne correspond
     */
    public Inscription getInscriptionById(Long id){
        return inscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable: " + id));
    }

    /**
     * Récupère toutes les inscriptions d'un élève donné.
     *
     * @param eleve élève ciblé
     * @return liste des inscriptions de l'élève
     */
    public List<Inscription> getInscriptionsByEleve(Eleve eleve) {
        return inscriptionRepository.findByEleve(eleve);
    }

    /**
     * Récupère toutes les inscriptions créées par un administrateur donné.
     *
     * @param admin utilisateur administrateur
     * @return liste des inscriptions
     */
    public List<Inscription> getInscriptionsByAdmin(Utilisateur admin) {
        return inscriptionRepository.findByUtilisateur(admin);
    }
}

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

    public List<Inscription> getAllInscriptions(){ return inscriptionRepository.findAll(); }

    // Variante contrôleur-friendly
    public InscriptionDTO createInscription(InscriptionCreateRequest request){
        // Création d'une nouvelle inscription avec les entités complètes
        Inscription inscription = new Inscription();

        // Récupération de l'élève complet
        Eleve eleve = eleveRepository.findById(request.getIdEleve())
                .orElseThrow(() -> new EntityNotFoundException("Élève introuvable : " + request.getIdEleve()));
        inscription.setEleve(eleve);

        // Récupération de l'utilisateur complet
        Utilisateur utilisateur = utilisateurRepository.findById(request.getIdUtilisateur())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable : " + request.getIdUtilisateur()));
        inscription.setUtilisateur(utilisateur);

        // Date d'inscription (optionnelle)
        if (request.getDateInscrip() != null) {
            inscription.setDateInscrip(request.getDateInscrip());
        }

        // Sauvegarde de l'inscription
        Inscription saved = createInscription(inscription);

        // Mise à jour des relations bidirectionnelles après sauvegarde pour éviter les problèmes de validation
        if (!eleve.getInscriptions().contains(saved)) {
            eleve.getInscriptions().add(saved);
        }
        if (!utilisateur.getInscriptions().contains(saved)) {
            utilisateur.getInscriptions().add(saved);
        }

        // Sauvegarde des entités mises à jour
        eleveRepository.save(eleve);
        utilisateurRepository.save(utilisateur);

        return InscriptionMapper.toDto(saved);
    }

    public Inscription createInscription(Inscription inscription){ return inscriptionRepository.save(inscription); }

    // Variante contrôleur-friendly: update avec request en entrée et DTO en sortie
    public InscriptionDTO editInscription(Long id, InscriptionDTO request) {
        Inscription current = getInscriptionById(id);
        Inscription updatedEntity = InscriptionMapper.applyUpdate(current, request);
        Inscription saved = editInscription(id, updatedEntity);
        return InscriptionMapper.toDto(saved);
    }

    public Inscription editInscription(Long id, Inscription inscription){
        // Récupération ou exception si non trouvé
        Inscription existing = inscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable : " + id));

        // Mise à jour des champs simples
        if (inscription.getDateInscrip() != null) {
            existing.setDateInscrip(inscription.getDateInscrip());
        }

        // Mise à jour de l'élève lié (si fourni)
        Eleve oldEleve = existing.getEleve();
        if (inscription.getEleve() != null && inscription.getEleve().getIdEleve() != null) {
            Eleve newEleve = eleveRepository.findById(inscription.getEleve().getIdEleve())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Élève introuvable : " + inscription.getEleve().getIdEleve()));

            // Gestion de la relation bidirectionnelle
            if (oldEleve != null && !oldEleve.getIdEleve().equals(newEleve.getIdEleve())) {
                oldEleve.getInscriptions().remove(existing);
                eleveRepository.save(oldEleve);
            }
            if (!newEleve.getInscriptions().contains(existing)) {
                newEleve.getInscriptions().add(existing);
            }
            existing.setEleve(newEleve);
            eleveRepository.save(newEleve);
        }

        // Mise à jour de l'utilisateur lié (si fourni)
        Utilisateur oldUtilisateur = existing.getUtilisateur();
        if (inscription.getUtilisateur() != null && inscription.getUtilisateur().getIdUtilisateur() != null) {
            Utilisateur newUtilisateur = utilisateurRepository.findById(inscription.getUtilisateur().getIdUtilisateur())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Utilisateur introuvable : " + inscription.getUtilisateur().getIdUtilisateur()));

            // Gestion de la relation bidirectionnelle
            if (oldUtilisateur != null && !oldUtilisateur.getIdUtilisateur().equals(newUtilisateur.getIdUtilisateur())) {
                oldUtilisateur.getInscriptions().remove(existing);
                utilisateurRepository.save(oldUtilisateur);
            }
            if (!newUtilisateur.getInscriptions().contains(existing)) {
                newUtilisateur.getInscriptions().add(existing);
            }
            existing.setUtilisateur(newUtilisateur);
            utilisateurRepository.save(newUtilisateur);
        }

        //Sauvegarde et retour
        return inscriptionRepository.save(existing);
    }

    public void deleteInscription(Long id){
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable: " + id));

        // Gestion des relations bidirectionnelles avant suppression
        Eleve eleve = inscription.getEleve();
        if (eleve != null && eleve.getInscriptions() != null) {
            eleve.getInscriptions().remove(inscription);
            eleveRepository.save(eleve);
        }

        Utilisateur utilisateur = inscription.getUtilisateur();
        if (utilisateur != null && utilisateur.getInscriptions() != null) {
            utilisateur.getInscriptions().remove(inscription);
            utilisateurRepository.save(utilisateur);
        }

        // Suppression de l'inscription
        inscriptionRepository.delete(inscription);
    }

    // Supprimer toutes les inscriptions d'un élève
    public void deleteAllInscriptionsForEleve(Eleve eleve) {
        List<Inscription> inscriptions = inscriptionRepository.findByEleve(eleve);
        for (Inscription inscription : inscriptions) {
            // Gestion des relations bidirectionnelles avant suppression
            Utilisateur utilisateur = inscription.getUtilisateur();
            if (utilisateur != null && utilisateur.getInscriptions() != null) {
                utilisateur.getInscriptions().remove(inscription);
                utilisateurRepository.save(utilisateur);
            }

            // Suppression de l'inscription
            inscriptionRepository.delete(inscription);
        }

        // Vider la liste des inscriptions de l'élève
        if (eleve.getInscriptions() != null) {
            eleve.getInscriptions().clear();
            eleveRepository.save(eleve);
        }
    }

    public Inscription getInscriptionById(Long id){
        return inscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription introuvable: " + id));
    }

    // Récupérer toutes les inscriptions d’un élève
    public List<Inscription> getInscriptionsByEleve(Eleve eleve) {
        return inscriptionRepository.findByEleve(eleve);
    }

    // Récupérer toutes les inscriptions faites par un administrateur
    public List<Inscription> getInscriptionsByAdmin(Utilisateur admin) {
        return inscriptionRepository.findByUtilisateur(admin);
    }
}
